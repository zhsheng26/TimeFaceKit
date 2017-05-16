/*
 * Copyright 2017 Zhihu Inc.
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
package cn.timeface.picker.internal.model;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import com.timeface.picker.R;
import cn.timeface.picker.internal.entity.MediaItem;
import cn.timeface.picker.internal.entity.SelectionSpec;
import cn.timeface.picker.internal.entity.IncapableCause;
import cn.timeface.picker.internal.ui.widget.CheckView;
import cn.timeface.picker.internal.utils.PhotoMetadataUtils;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unused")
public class SelectedItemCollection {

    public static final String STATE_SELECTION = "state_selection";
    public static final String STATE_COLLECTION_TYPE = "state_collection_type";
    private final Context mContext;
    private Set<MediaItem> mMediaItems;

    /**
     * Empty collection
     */
    public static final int COLLECTION_UNDEFINED = 0x00;
    /**
     * Collection only with images
     */
    public static final int COLLECTION_IMAGE = 0x01;
    /**
     * Collection only with videos
     */
    public static final int COLLECTION_VIDEO = 0x01 << 1;
    /**
     * Collection with images and videos.
     */
    public static final int COLLECTION_MIXED = COLLECTION_IMAGE | COLLECTION_VIDEO;

    private int mCollectionType = COLLECTION_UNDEFINED;

    public SelectedItemCollection(Context context) {
        mContext = context;
    }

    public void onCreate(Bundle bundle) {
        if (bundle == null) {
            mMediaItems = new LinkedHashSet<>();
        } else {
            List<MediaItem> saved = bundle.getParcelableArrayList(STATE_SELECTION);
            mMediaItems = new LinkedHashSet<>(saved);
            mCollectionType = bundle.getInt(STATE_COLLECTION_TYPE, COLLECTION_UNDEFINED);
        }
    }

    public void setDefaultSelection(List<MediaItem> uris) {
        mMediaItems.addAll(uris);
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(mMediaItems));
        outState.putInt(STATE_COLLECTION_TYPE, mCollectionType);
    }

    public Bundle getDataWithBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(STATE_SELECTION, new ArrayList<>(mMediaItems));
        bundle.putInt(STATE_COLLECTION_TYPE, mCollectionType);
        return bundle;
    }

    public boolean add(MediaItem mediaItem) {
        if (typeConflict(mediaItem)) {
            throw new IllegalArgumentException("Can't select images and videos at the same time.");
        }
        boolean added = mMediaItems.add(mediaItem);
        if (added) {
            if (mCollectionType == COLLECTION_UNDEFINED) {
                if (mediaItem.isImage()) {
                    mCollectionType = COLLECTION_IMAGE;
                } else if (mediaItem.isVideo()) {
                    mCollectionType = COLLECTION_VIDEO;
                }
            } else if (mCollectionType == COLLECTION_IMAGE) {
                if (mediaItem.isVideo()) {
                    mCollectionType = COLLECTION_MIXED;
                }
            } else if (mCollectionType == COLLECTION_VIDEO) {
                if (mediaItem.isImage()) {
                    mCollectionType = COLLECTION_MIXED;
                }
            }
        }
        return added;
    }

    public boolean remove(MediaItem mediaItem) {
        boolean removed = mMediaItems.remove(mediaItem);
        if (removed) {
            if (mMediaItems.size() == 0) {
                mCollectionType = COLLECTION_UNDEFINED;
            } else {
                if (mCollectionType == COLLECTION_MIXED) {
                    refineCollectionType();
                }
            }
        }
        return removed;
    }

    public void overwrite(ArrayList<MediaItem> mediaItems, int collectionType) {
        if (mediaItems.size() == 0) {
            mCollectionType = COLLECTION_UNDEFINED;
        } else {
            mCollectionType = collectionType;
        }
        mMediaItems.clear();
        mMediaItems.addAll(mediaItems);
    }


    public List<MediaItem> asList() {
        return new ArrayList<>(mMediaItems);
    }

    public List<Uri> asListOfUri() {
        List<Uri> uris = new ArrayList<>();
        for (MediaItem mediaItem : mMediaItems) {
            uris.add(mediaItem.getContentUri());
        }
        return uris;
    }

    public boolean isEmpty() {
        return mMediaItems == null || mMediaItems.isEmpty();
    }

    public boolean isSelected(MediaItem mediaItem) {
        return mMediaItems.contains(mediaItem);
    }

    public IncapableCause isAcceptable(MediaItem mediaItem) {
        if (maxSelectableReached()) {
            return new IncapableCause(mContext.getString(R.string.error_over_count,
                    SelectionSpec.getInstance().maxSelectable));
        } else if (typeConflict(mediaItem)) {
            return new IncapableCause(mContext.getString(R.string.error_type_conflict));
        }

        return PhotoMetadataUtils.isAcceptable(mContext, mediaItem);
    }

    public boolean maxSelectableReached() {
        return mMediaItems.size() == SelectionSpec.getInstance().maxSelectable;
    }

    public int getCollectionType() {
        return mCollectionType;
    }

    private void refineCollectionType() {
        boolean hasImage = false;
        boolean hasVideo = false;
        for (MediaItem i : mMediaItems) {
            if (i.isImage() && !hasImage) hasImage = true;
            if (i.isVideo() && !hasVideo) hasVideo = true;
        }
        if (hasImage && hasVideo) {
            mCollectionType = COLLECTION_MIXED;
        } else if (hasImage) {
            mCollectionType = COLLECTION_IMAGE;
        } else if (hasVideo) {
            mCollectionType = COLLECTION_VIDEO;
        }
    }

    /**
     * Determine whether there will be conflict media types. A user can only select images and videos at the same time
     * while {@link SelectionSpec#mediaTypeExclusive} is set to false.
     */
    public boolean typeConflict(MediaItem mediaItem) {
        return SelectionSpec.getInstance().mediaTypeExclusive
                && ((mediaItem.isImage() && (mCollectionType == COLLECTION_VIDEO || mCollectionType == COLLECTION_MIXED))
                || (mediaItem.isVideo() && (mCollectionType == COLLECTION_IMAGE || mCollectionType == COLLECTION_MIXED)));
    }

    public int count() {
        return mMediaItems.size();
    }

    public int checkedNumOf(MediaItem mediaItem) {
        int index = new ArrayList<>(mMediaItems).indexOf(mediaItem);
        return index == -1 ? CheckView.UNCHECKED : index + 1;
    }
}
