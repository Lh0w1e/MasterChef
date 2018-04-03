package version2.masterchef.com.masterchef.Adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import version2.masterchef.com.masterchef.Utils.OnTapListener;
import version2.masterchef.com.masterchef.Utils.RecyclerViewSetViewHolder;
import version2.masterchef.com.masterchef.Models.FavoritesModel;
import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 10/10/2017.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerViewSetViewHolder> {

    private Activity mActivity;
    List<FavoritesModel> favoritesModels = Collections.emptyList();

    private OnTapListener onTapListener;

    public FavoritesAdapter(Activity mActivity, List<FavoritesModel> favoritesModels) {
        this.mActivity = mActivity;
        this.favoritesModels = favoritesModels;
    }
    public RecyclerViewSetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favorites, parent, false);

        return new RecyclerViewSetViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewSetViewHolder holder, final int position) {

        holder.favorite_name.setText(favoritesModels.get(position).getRecipe_name());
        holder.favorite_image.setImageBitmap(favoritesModels.get(position).getRecipe_image());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onTapListener != null){
                    onTapListener.onTapView(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return favoritesModels.size();
    }

    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}




