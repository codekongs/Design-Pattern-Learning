package cn.codekong.imageloader.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 尚振鸿 on 17-12-2. 22:09
 * mail:szh@codekong.cn
 */

public class ImageLoader {
    /**
     * 图片缓存
     */
    private LruCache<String, Bitmap> mImageCache;
    /**
     * 线程池,线程数量为CPU数量
     */
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());

    public ImageLoader() {
        initImageCache();
    }

    private void initImageCache() {
        //计算可使用的最大内存
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //取四分之一可用内存作为缓存
        final int cacheSize = maxMemory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public void displayImage(final String url, final ImageView imageView){
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
            Log.d("HHHHH", "getImage: 从缓存读取图片...");
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                final Bitmap imageBitmap = downloadImage(url);
                if (imageBitmap == null){
                    return;
                }

                if (imageView.getTag().equals(url)){
                    imageView.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(imageBitmap);
                            mImageCache.put(url, imageBitmap);
                            Log.d("HHHHH", "cache: 缓存图片完成...");
                        }
                    });
                }
            }
        });
    }

    /**
     * 根据url下载图片
     * @param imageUrl 图片url
     * @return 图片
     */
    public Bitmap downloadImage(String imageUrl) {
        Log.d("HHHHH", "downloadImage: size " + mImageCache.size());
        Log.d("HHHHH", "downloadImage: 正在下载图片...");
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("HHHHH", "downloadImage: 下载完成...");
        return bitmap;
    }
}
