/*
 *    Copyright 2023 Aditya Bavadekar
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.adityaamolbavadekar.messenger.dialogs

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.adityaamolbavadekar.messenger.R
import com.adityaamolbavadekar.messenger.databinding.*
import com.adityaamolbavadekar.messenger.managers.InternetManager
import com.adityaamolbavadekar.messenger.managers.PrefsManager
import com.adityaamolbavadekar.messenger.model.ReactionRecord
import com.adityaamolbavadekar.messenger.model.Recipient
import com.adityaamolbavadekar.messenger.model.valueOf
import com.adityaamolbavadekar.messenger.utils.extensions.load
import com.adityaamolbavadekar.messenger.utils.logging.InternalLogger
import com.adityaamolbavadekar.messenger.utils.theming.ThemeInfo
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView

object Dialogs {

    fun showLoadingDialog(
        context: Context,
        message: String? = null, show: Boolean = true
    ): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setMessage(message)
            .setView(R.layout.dialog_loading)
            .create()
        if (show) dialog.show()
        return dialog
    }

    fun showWallpaperDialog(
        context: Context,
        shouldRemoveWallpaper: (Boolean) -> Unit
    ): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(R.string.wallpaper)
            .setItems(
                arrayOf(
                    context.getString(R.string.remove_wallpaper),
                    context.getString(R.string.change_wallpaper)
                )
            )
            { b, i ->
                b.dismiss()
                if (i == 0) shouldRemoveWallpaper(true)
                else shouldRemoveWallpaper(false)
            }
            .create()
        dialog.show()
        return dialog
    }

    fun showProfilePictureDialog(
        context: Context,
        url: String,
        name: String,
        onClick: View.OnClickListener?
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val view = DialogProfilePictureBinding.inflate(LayoutInflater.from(context), null, false)
        view.imageView.load(Uri.parse(url), false)
        view.imageView.setOnClickListener(onClick)
        view.title.text = name
        view.close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(view.root)
        dialog.show()
        return dialog

    }

    fun showReactionChooserDialog(
        context: Context,
        onShouldSaveReaction: (String) -> Unit
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val view = DialogReactionChooserBinding.inflate(LayoutInflater.from(context), null, false)
        view.reactionEditText.addTextChangedListener {
            view.button.isEnabled = it != null && it.toString().trim().isNotEmpty()
        }
        view.button.setOnClickListener {
            val reaction = view.reactionEditText.text.toString()
            dialog.dismiss()
            onShouldSaveReaction(reaction)
        }
        view.close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }


    fun showAddAttachmentDialog(
        context: Context,
        listener: NavigationView.OnNavigationItemSelectedListener
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val view = DialogAddAttachmentBinding.inflate(LayoutInflater.from(context), null, false)
        view.nav.setNavigationItemSelectedListener {
            dialog.dismiss()
            listener.onNavigationItemSelected(it)
            true
        }
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }

    fun showReactionInfoDialog(
        context: Context,
        reactions: List<ReactionRecord>,
        recipients: List<Recipient>
    ): BottomSheetDialog {
        val dialog = BottomSheetDialog(context)
        val view = DialogReactionsViewierBinding.inflate(LayoutInflater.from(context), null, false)
        var text = ""

        reactions.forEach { reaction ->
            recipients.valueOf(reaction.reactorUid)?.let { reactor ->
                text += context.getString(
                    R.string.reactor_reacted_formatted,
                    reactor.loadFirstName(),
                    reaction.reaction
                ) + "\n"
            }
        }
        view.textView.text = text
        view.close.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setContentView(view.root)
        dialog.show()
        return dialog
    }

    fun showPermissionRequestDialog(
        context: Context,
        @DrawableRes icon: Int,
        title: String,
        message: String,
        shouldContinue: (Boolean) -> Unit
    ): AlertDialog {
        val view = DialogPermissionRequestBinding.inflate(LayoutInflater.from(context), null, false)
        view.imageView.setImageResource(icon)
        view.message.text = message
        view.title.text = title
        view.button.setOnClickListener {
            shouldContinue(true)
        }
        view.buttonNegative.setOnClickListener {
            shouldContinue(false)
        }
        val dialog = MaterialAlertDialogBuilder(context)
            .setView(view.root)
            .create()
        dialog.show()
        return dialog

    }

    fun showErrorDialog(
        context: Context,
        message: String,
        title: String = context.getString(R.string.a_problem_occurred),
        buttonText: String = context.getString(R.string.try_again),
        onClick: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
//            .setBackground(context.getDrawable(R.drawable.loading_dialog_background_error)!!)
            .setOnDismissListener { onClick() }
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(buttonText) { d, _ ->
                d.dismiss()
            }
            .create()
            .show()
    }

    fun showAccountCreatedDialog(
        context: Context,
        message: String = context.getString(R.string.your_account_has_been_created_successfully),
        title: String = context.getString(R.string.signup),
        buttonText: String = context.getString(R.string.continue_text),
        onClick: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(buttonText) { d, _ ->
                d.dismiss()
                onClick()
            }
            .create()
            .show()
    }

    fun showConfirmPhoneNumberDialog(
        context: Context,
        phoneNumber: String,
        message: String = context.getString(
            R.string.phone_number_confirmation_dialog_formatted,
            phoneNumber
        ),
        shouldContinue: (Boolean) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setMessage(message)
            .setNeutralButton(context.getString(R.string.edit_number)) { d, _ ->
                d.dismiss()
                shouldContinue(false)
            }
            .setPositiveButton(context.getString(R.string.yes)) { d, _ ->
                d.dismiss()
                shouldContinue(true)
            }
            .create()
            .show()
    }


    fun showRemoveImageDialog(
        context: Context,
        message: String = context.getString(
            R.string.remove_image_dialog
        ),
        shouldRemoveImage: (Boolean) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setMessage(message)
            .setNeutralButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
                shouldRemoveImage(false)
            }
            .setPositiveButton(context.getString(R.string.yes)) { d, _ ->
                d.dismiss()
                shouldRemoveImage(true)
            }
            .create()
            .show()
    }


    fun showConfirmLinkDialog(
        context: Context,
        link: String,
        message: String = context.getString(
            R.string.phone_number_confirmation_dialog_formatted,
            link
        ),
        shouldContinue: (Boolean) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setMessage(message)
            .setNeutralButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
                shouldContinue(false)
            }
            .setPositiveButton(context.getString(R.string.yes)) { d, _ ->
                d.dismiss()
                shouldContinue(true)
            }
            .create()
            .show()
    }


    fun showRemoveRecipientDialog(
        context: Context,
        recipientName: String,
        message: String = context.getString(
            R.string.remove_participant_formatted,
            recipientName
        ),
        shouldContinue: (Boolean) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setMessage(message)
            .setNeutralButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
                shouldContinue(false)
            }
            .setPositiveButton(context.getString(R.string.yes)) { d, _ ->
                d.dismiss()
                shouldContinue(true)
            }
            .create()
            .show()
    }


    fun showDeleteConversationDialog(
        context: Context,
        shouldContinue: (Boolean) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(context.getString(R.string.delete_conversation_title))
            .setMessage(context.getString(R.string.delete_conversation_message))
            .setNeutralButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
                shouldContinue(false)
            }
            .setPositiveButton(context.getString(R.string.delete)) { d, _ ->
                d.dismiss()
                shouldContinue(true)
            }
            .create()
            .show()
    }

    fun showLogoutDialog(
        context: Context,
        shouldLogout: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(context.getString(R.string.logout_question))
            .setMessage(
                context.getString(
                    R.string.logout_confirmation_message,
                    PrefsManager(context).getUserEmail() ?: "Account"
                )
            )
            .setNeutralButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
            }
            .setPositiveButton(context.getString(R.string.logout)) { d, _ ->
                d.dismiss()
                shouldLogout()
            }
            .create()
            .show()
    }


    fun showLoggedOutDialog(
        context: Context,
        onClick: () -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setTitle(context.getString(R.string.logged_out))
            .setMessage(
                context.getString(
                    R.string.logged_out_message,
                    PrefsManager(context).getUserFullName() ?: ""
                )
            )
            .setPositiveButton(context.getString(R.string.okay)) { d, _ ->
                d.dismiss()
                onClick()
            }
            .create()
            .show()
    }

    fun createAutoDismissNetworkStatusDialog(
        context: AppCompatActivity
    ) {
        val tag = context.javaClass.simpleName + "#NetworkStatusDialog"
        val dialog = MaterialAlertDialogBuilder(context)
            .setCancelable(false)
            .setMessage(context.getString(R.string.please_check_your_network_connection_and_try_again))
            .setTitle(context.getString(R.string.network_unavailable))
            //.setIcon(R.drawable.ic_no_signal)
            .create()
        InternetManager.isConnected.observe(context) { isConnected ->
            if (isConnected) {
                if (dialog.isShowing) {
                    InternalLogger.logW(
                        tag,
                        "Internet:Connected [Hiding Dialog]"
                    )
                    dialog.hide()
                }
            } else {
                InternalLogger.logW(
                    tag,
                    "Internet:Not Connected! [Showing Dialog]"
                )
                dialog.show()
            }
        }
    }

    fun showThemeChooserDialog(context: Context, onChanged: () -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(context.getString(R.string.choose_theme))
            .setItems(R.array.theme_chooser) { d, i ->
                ThemeInfo.valueOf(i).save(context)
                d.dismiss()
                onChanged()
            }
            .setNegativeButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
            }
            .create()
            .show()
    }

    fun showItemChooserDialog(
        context: Context,
        title: Int,
        arrayInt: Int,
        checkedItemId: Int,
        onChanged: (Int) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setCancelable(true)
            .setTitle(title)
            .setSingleChoiceItems(arrayInt, checkedItemId) { d, i ->
                d.dismiss()
                onChanged(i)
            }
            .setNegativeButton(context.getString(R.string.cancel)) { d, _ ->
                d.dismiss()
            }
            .setPositiveButton(context.getString(R.string.okay)) { d, _ ->
                d.dismiss()
            }
            .create()
            .show()
    }

}