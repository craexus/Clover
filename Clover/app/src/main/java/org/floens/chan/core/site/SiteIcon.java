/*
 * Clover - 4chan browser https://github.com/Floens/Clover/
 * Copyright (C) 2014  Floens
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.floens.chan.core.site;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.IOException;

import okhttp3.HttpUrl;

import static org.floens.chan.utils.AndroidUtils.getAppContext;
import static org.floens.chan.utils.AndroidUtils.getRes;

public class SiteIcon {
    private String assetPath;
    private HttpUrl url;

    public static SiteIcon fromAssets(String path) {
        SiteIcon siteIcon = new SiteIcon();
        siteIcon.assetPath = path;
        return siteIcon;
    }

    public static SiteIcon fromUrl(HttpUrl url) {
        SiteIcon siteIcon = new SiteIcon();
        siteIcon.url = url;
        return siteIcon;
    }

    public void get(SiteIconResult result) {
        if (assetPath != null) {
            Bitmap bitmap;
            try {
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inScaled = false;
                bitmap = BitmapFactory.decodeStream(getAppContext().getAssets().open(assetPath), null, opts);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BitmapDrawable drawable = new BitmapDrawable(getRes(), bitmap);
            drawable = (BitmapDrawable) drawable.mutate();
            drawable.getPaint().setFilterBitmap(false);
            result.onSiteIcon(this, drawable);
        } else if (url != null) {
            // TODO
        }
    }

    public interface SiteIconResult {
        void onSiteIcon(SiteIcon siteIcon, Drawable icon);
    }
}
