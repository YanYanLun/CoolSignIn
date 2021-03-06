/*
 * Copyright 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hufeiya.SignIn.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.hufeiya.SignIn.model.Avatar;
import com.hufeiya.SignIn.model.User;


/**
 * Easy storage and retrieval of preferences.
 */
public class PreferencesHelper {

    private static final String USER_PREFERENCES = "playerPreferences";
    private static final String PREFERENCE_PHONE = USER_PREFERENCES + ".phone";
    private static final String PREFERENCE_PASS = USER_PREFERENCES + ".pass";
    private static final String PREFERENCE_AVATAR = USER_PREFERENCES + ".avatar";

    private PreferencesHelper() {
        //no instancer
    }

    /**
     * Writes a {@link User} to preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @param user  The {@link User} to write.
     */
    public static void writeToPreferences(Context context, User user) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_PHONE, user.getPhone());
        editor.putString(PREFERENCE_PASS, user.getPass());
        editor.putString(PREFERENCE_AVATAR, user.getAvatar().name());
        editor.apply();
    }

    /**
     * Retrieves a {@link User} from preferences.
     *
     * @param context The Context which to obtain the SharedPreferences from.
     * @return A previously saved player or <code>null</code> if none was saved previously.
     */
    public static User getPlayer(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String firstName = preferences.getString(PREFERENCE_PHONE, null);
        final String lastInitial = preferences.getString(PREFERENCE_PASS, null);
        final String avatarPreference = preferences.getString(PREFERENCE_AVATAR, null);
        final Avatar avatar;
        if (null != avatarPreference) {
            avatar = Avatar.valueOf(avatarPreference);
        } else {
            avatar = null;
        }

        if (null == firstName && null == lastInitial && null == avatar) {
            return null;
        }
        return new User(firstName, lastInitial, avatar);
    }

    /**
     * Signs out a player by removing all it's data.
     *
     * @param context The context which to obtain the SharedPreferences from.
     */
    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_PHONE);
        editor.remove(PREFERENCE_PASS);
        editor.remove(PREFERENCE_AVATAR);
        editor.apply();
    }

    /**
     * Check whether a user is currently signed in.
     *
     * @param context The context to check this in.
     * @return <code>true</code> if login data exists, else <code>false</code>.
     */
    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCE_PHONE) &&
                preferences.contains(PREFERENCE_PASS) &&
                preferences.contains(PREFERENCE_AVATAR);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
    }
}
