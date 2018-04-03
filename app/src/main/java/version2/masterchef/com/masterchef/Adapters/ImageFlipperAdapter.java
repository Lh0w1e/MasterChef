package version2.masterchef.com.masterchef.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 10/6/2017.
 */
public class ImageFlipperAdapter extends BaseAdapter {
    Context context;
    Bitmap[] images;
    String[] names;
    LayoutInflater inflater;

    private Animation goLeft, goRight;

    public ImageFlipperAdapter(Context context, Bitmap[] images, String[] names, Animation goLeft, Animation goRight) {
        this.context = context;
        this.names = names;
        this.images = images;
        this.inflater = LayoutInflater.from(context);
        this.goLeft = goLeft;
        this.goRight = goRight;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = inflater.inflate(R.layout.flipper_items, null);
        TextView mRecipeName = (TextView) mView.findViewById(R.id.flipper_name);
        ImageView mImage = (ImageView) mView.findViewById(R.id.flipper_image);

        mRecipeName.setText(names[position]);
        mImage.setImageBitmap(images[position]);

        mRecipeName.startAnimation(goRight);
        mImage.startAnimation(goLeft);

        return mView;
    }
}
