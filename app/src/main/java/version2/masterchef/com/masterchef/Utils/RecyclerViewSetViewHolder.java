package version2.masterchef.com.masterchef.Utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 10/10/2017.
 */
public class RecyclerViewSetViewHolder extends RecyclerView.ViewHolder {

    public ImageView favorite_image;
    public TextView favorite_name;

    public RecyclerViewSetViewHolder(View itemView) {
        super(itemView);

        favorite_image = (ImageView) itemView.findViewById(R.id.custom_favorite_image);
        favorite_name = (TextView) itemView.findViewById(R.id.custom_favorite_name);

    }
}
