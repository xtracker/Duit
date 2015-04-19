package com.ms.duit.utils.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.ms.duit.DuitApplication;

import java.lang.ref.WeakReference;

/**
 * Created by jarzhao on 4/8/2015.
 */
public class BitmapHelper {

    private static LruCache<String, Bitmap> s_imageMemoryCache;

    private static void InitImageCache() {
        if (s_imageMemoryCache == null) {
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;

            s_imageMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    // The cache size will be measured in kilobytes rather than
                    // number of items.
                    return bitmap.getByteCount() / 1024;
                }
            };
        }
    }

     public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / inSampleSize;

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 2;

            while (totalPixels > totalReqPixelsCap) {
                inSampleSize *= 2;
                totalPixels /= 2;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }

    public static void loadBitmap(ImageView imageView, String path, int width, int height) {

        InitImageCache();
        if (s_imageMemoryCache.get(path) != null) {
            imageView.setImageBitmap(s_imageMemoryCache.get(path));
        } else {
            BitmapWorkerTask task = new BitmapWorkerTask(imageView, path, width, height);
            task.execute();
        }


    }

    public static Bitmap decodeSampledBitmapFromResource(String path, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static class BitmapWorkerTask extends AsyncTask<Void, Void, BitmapDrawable> {

        private final WeakReference<ImageView> imageViewWeakReference;
        private final Object mData;
        private final int mWidth, mHeight;
        public BitmapWorkerTask(ImageView imageView, Object data, int width, int height) {
            super();
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
            mData = data;
            mWidth = width;
            mHeight = height;
        }
        @Override
        protected BitmapDrawable doInBackground(Void... params) {
            if (isCancelled())
                return null;

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Bitmap bmp = decodeSampledBitmapFromResource((String)mData, mWidth, mHeight);

            return new BitmapDrawable(DuitApplication.getAppContext().getResources(), /*ThumbnailUtils.extractThumbnail(*/bmp/*, mWidth, mHeight)*/);
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            ImageView imageView = imageViewWeakReference.get();
            if (imageView != null) {
                imageView.setImageDrawable(bitmapDrawable);
            }

            String key = (String)mData;
            if (s_imageMemoryCache.get(key) == null) {
                s_imageMemoryCache.put(key, bitmapDrawable.getBitmap());
            }
        }

        @Override
        protected void onCancelled(BitmapDrawable bitmapDrawable) {
            super.onCancelled(bitmapDrawable);
        }
    }
}
