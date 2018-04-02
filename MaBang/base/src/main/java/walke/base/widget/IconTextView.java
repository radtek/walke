package walke.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
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
public class IconTextView extends LinearLayout {

    private ImageView img;
    private TextView text;

    public IconTextView(Context context) {
        this(context,null);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.base_view_icon_text,this);
        this.setOrientation(LinearLayout.HORIZONTAL);
        img = ((ImageView) findViewById(R.id.icon_text_image));
        text = ((TextView) findViewById(R.id.icon_text_text));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IconTextView, defStyleAttr, 0);
        int n = a.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.IconTextView_img) {
                int resourceId = a.getResourceId(attr, R.mipmap.ic_launcher);
                img.setImageResource(resourceId);

            } else if (attr == R.styleable.IconTextView_text) {
                String str = a.getString(attr);
                if (!TextUtils.isEmpty(str))
                    text.setText(str);

            } else if (attr == R.styleable.IconTextView_textsColor) {
                int color = a.getColor(attr, Color.BLACK);
                text.setTextColor(color);

            }
        }
        a.recycle();
    }

    public ImageView getImg() {
        return img;
    }

    public TextView getText() {
        return text;
    }
}
