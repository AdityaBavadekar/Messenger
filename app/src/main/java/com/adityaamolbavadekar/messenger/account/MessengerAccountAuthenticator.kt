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

package com.adityaamolbavadekar.messenger.account

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.adityaamolbavadekar.messenger.managers.AuthManager
import com.adityaamolbavadekar.messenger.ui.registration.AuthActivity
import com.adityaamolbavadekar.messenger.utils.Constants

class MessengerAccountAuthenticator(private val context: Context) :
    AbstractAccountAuthenticator(context) {

    private val authManager = AuthManager()

    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String?
    ): Bundle? {
        return null
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<out String>?,
        options: Bundle?
    ): Bundle? {
        if (accountType != MessengerAccountInfo.ACCOUNT_TYPE || response == null) return null
        if (authManager.isLoggedIn) {
            val b = Bundle()
            b.putString(AccountManager.KEY_ACCOUNT_TYPE, MessengerAccountInfo.ACCOUNT_TYPE)
            b.putString(
                AccountManager.KEY_ACCOUNT_NAME,
                authManager.getFirebaseUser?.phoneNumber
            )
            return b
        }
        return internalAddAccount(response)
    }

    private fun internalAddAccount(response: AccountAuthenticatorResponse?): Bundle {
        val result = Bundle()
        Intent(context, AuthActivity::class.java).also { intent ->
            intent.action = Constants.Actions.ACTION_LOGIN_ACCOUNT_MANAGER_ADD
            intent.putExtra(AccountManager.KEY_ACCOUNT_MANAGER_RESPONSE, response)
            result.putParcelable(AccountManager.KEY_INTENT, intent)
        }
        return result
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        options: Bundle?
    ): Bundle? {
        return null
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle? {
        return null
    }

    override fun getAuthTokenLabel(authTokenType: String?): String {
        return authTokenType ?: ""
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        authTokenType: String?,
        options: Bundle?
    ): Bundle? {
        return null
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse?,
        account: Account?,
        features: Array<out String>?
    ): Bundle? {
        return null
    }

    companion object {

        fun account(name: String?): Account {
            val accountName = name ?: MessengerAccountInfo.ACCOUNT_NAME
            return Account(accountName, MessengerAccountInfo.ACCOUNT_TYPE)
        }

        fun account(context: Context): Account? {
            return accountManager(context).getAccountsByType(MessengerAccountInfo.ACCOUNT_TYPE).firstOrNull()
        }

        fun accountManager(context: Context): AccountManager {
            return AccountManager.get(context)
        }

        fun deleteAccount(account: Account, context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                internalDeleteAccount(account, context)
            else false
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
        fun internalDeleteAccount(account: Account, context: Context): Boolean {
            return accountManager(context).removeAccountExplicitly(account)
        }

        fun addAccount(account: Account, context: Context): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                internalAddAccount(account, context)
            else false
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun internalAddAccount(account: Account, context: Context): Boolean {
            return accountManager(context)
                .addAccountExplicitly(account, null, null)
        }

    }

}