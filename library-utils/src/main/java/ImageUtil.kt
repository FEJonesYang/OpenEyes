import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * @author: Jony
 * @date: 2022/5/14
 * @description: 图片的工具类
 */
object ImageUtil {

    /**
     * 从网络获取 Bitmap 类型的图片,
     */
    fun getBitMapFromNetwork(uri: String): Bitmap {
        val url = URL(uri)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000
        connection.doInput = true
        connection.useCaches = true
        // 得到数据流,进行解析
        val inputStream: InputStream = connection.inputStream
        val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()
        return bitmap
    }

    /**
     * 通过 Glide 加载图片，占位符根据不同业务具体加载，后期可扩展
     * @param context 上下文
     * @param url 链接
     */
    fun loadImage(context: Context, url: String): RequestBuilder<Drawable> {
        return Glide.with(context).load(url)
    }

    /**
     *  @param roundingRadius 圆角的弧度
     *  加载带有圆角的图片
     */
    fun loadRoundedImage(
        context: Context,
        url: String,
        roundingRadius: Int
    ): RequestBuilder<Drawable> {
        return Glide.with(context).load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(roundingRadius)))
    }

    /**
     * 加载标准的原型图片
     */
    fun loadCircleImage(context: Context, url: String): RequestBuilder<Drawable> {
        return Glide.with(context).load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
    }

}