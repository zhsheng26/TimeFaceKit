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
package cn.timeface.picker.internal.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.timeface.picker.R;
import cn.timeface.picker.internal.entity.MediaItem;
import cn.timeface.picker.internal.entity.SelectionSpec;
import cn.timeface.picker.internal.utils.PhotoMetadataUtils;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

public class PreviewItemFragment extends Fragment {

    private static final String ARGS_ITEM = "args_item";

    public static PreviewItemFragment newInstance(MediaItem mediaItem) {
        PreviewItemFragment fragment = new PreviewItemFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGS_ITEM, mediaItem);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_preview_item, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final MediaItem mediaItem = getArguments().getParcelable(ARGS_ITEM);
        if (mediaItem == null) {
            return;
        }

        View videoPlayButton = view.findViewById(R.id.video_play_button);
        if (mediaItem.isVideo()) {
            videoPlayButton.setVisibility(View.VISIBLE);
            videoPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(mediaItem.uri, "video/*");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        Toast.makeText(getContext(), R.string.error_no_video_activity, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            videoPlayButton.setVisibility(View.GONE);
        }

        ImageViewTouch image = (ImageViewTouch) view.findViewById(R.id.image_view);
        image.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);

        Point size = PhotoMetadataUtils.getBitmapSize(mediaItem.getContentUri(), getActivity());
        if (mediaItem.isGif()) {
            SelectionSpec.getInstance().imageEngine.loadGifImage(getContext(), size.x, size.y, image,
                    mediaItem.getContentUri());
        } else {
            SelectionSpec.getInstance().imageEngine.loadImage(getContext(), size.x, size.y, image,
                    mediaItem.getContentUri());
        }
    }

    public void resetView() {
        if (getView() != null) {
            ((ImageViewTouch) getView().findViewById(R.id.image_view)).resetMatrix();
        }
    }
}
