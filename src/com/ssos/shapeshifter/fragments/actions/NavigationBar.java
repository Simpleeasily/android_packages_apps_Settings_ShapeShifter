/*
 * Copyright (C) 2020 ShapeShiftOS
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

package com.ssos.shapeshifter.fragments.actions;

import android.content.Context;
import android.os.Bundle;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.Indexable;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

import com.android.internal.logging.nano.MetricsProto;

import com.ssos.support.preferences.SystemSettingSwitchPreference;
import com.ssos.support.preferences.SecureSettingSwitchPreference;

import java.util.ArrayList;
import java.util.List;

@SearchIndexable
public class NavigationBar extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener, Indexable {

    private static final String PIXEL_ANIMATION_NAVIGATION = "pixel_nav_animation";
    private static final String INVERT_NAVIGATION = "sysui_nav_bar_inverse";

    private SystemSettingSwitchPreference mPixelAnimationNavigation;
    private SecureSettingSwitchPreference mInvertNavigation;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.navigation_bar);

        mPixelAnimationNavigation = findPreference(PIXEL_ANIMATION_NAVIGATION);
        mInvertNavigation = findPreference(INVERT_NAVIGATION);
        // On three button nav
        if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.internal.systemui.navbar.threebutton")) {
            mPixelAnimationNavigation.setSummary(getString(R.string.pixel_navbar_anim_summary));
            mInvertNavigation.setSummary(getString(R.string.navigation_bar_invert_layout_summary));
        // On two button nav
        } else if (com.android.internal.util.ssos.Utils.isThemeEnabled("com.android.internal.systemui.navbar.twobutton")) {
            mPixelAnimationNavigation.setSummary(getString(R.string.pixel_navbar_anim_summary));
            mInvertNavigation.setSummary(getString(R.string.navigation_bar_invert_layout_summary));
        // On gesture nav
        } else {
            mPixelAnimationNavigation.setSummary(getString(R.string.unsupported_gestures));
            mInvertNavigation.setSummary(getString(R.string.unsupported_gestures));
            mPixelAnimationNavigation.setEnabled(false);
            mInvertNavigation.setEnabled(false);
        }
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.CUSTOM_SETTINGS;
    }

    public static final SearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
        new BaseSearchIndexProvider() {
            @Override
            public List<SearchIndexableResource> getXmlResourcesToIndex(Context context,
                    boolean enabled) {
                final ArrayList<SearchIndexableResource> result = new ArrayList<>();
                final SearchIndexableResource sir = new SearchIndexableResource(context);
                sir.xmlResId = R.xml.navigation_bar;
                result.add(sir);
                return result;
            }

            @Override
            public List<String> getNonIndexableKeys(Context context) {
                final List<String> keys = super.getNonIndexableKeys(context);
                return keys;
            }
    };
}
