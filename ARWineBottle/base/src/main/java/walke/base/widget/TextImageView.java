package walke.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import walke.base.R;


/**
 * Created by Walke.Z
 * on 2017/6/16. 20.
 * email：1126648815@qq.com
 */
public class TextImageView extends LinearLayout {

    private ImageView img,ivLayer;
    private TextView tvTitle,tvDesc;

    public TextImageView(Context context) {
        this(context,null);
    }

    public TextImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_text_image,this);
        img = ((ImageView) findViewById(R.id.vti_ivBg));
        ivLayer = ((ImageView) findViewById(R.id.vti_ivLayer));
        tvTitle = ((TextView) findViewById(R.id.vti_tvTitle));
        tvDesc = ((TextView) findViewById(R.id.vti_tvDesc));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextImageView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.TextImageView_img) {
                int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
                img.setImageResource(resourceId);
            } else if (attr == R.styleable.TextImageView_ivLayer) {
                int resourceId = a.getResourceId(attr, R.color.transparent);
                ivLayer.setImageResource(resourceId);
            } else if (attr == R.styleable.TextImageView_text) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    tvTitle.setText(str);
            } else if (attr == R.styleable.TextImageView_desc) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    tvDesc.setText(str);
            }
        }
        a.recycle();
    }

    public ImageView getImg() {
        return img;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvDesc() {
        return tvDesc;
    }
}
