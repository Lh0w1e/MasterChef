package version2.masterchef.com.masterchef.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import version2.masterchef.com.masterchef.Models.FavoritesModel;
import version2.masterchef.com.masterchef.R;
import version2.masterchef.com.masterchef.Utils.OnTapListener;

/**
 * Created by Colinares on 10/14/2017.
 */
public class FavoritesAdapterV2 extends RecyclerView.Adapter<FavoritesAdapterV2.MyViewHolder> {

    private ArrayList<FavoritesModel> favoritesModels = new ArrayList<>();
    private OnTapListener onTapListener;


    public FavoritesAdapterV2(ArrayList<FavoritesModel> favoritesModels) {
        this.favoritesModels = favoritesModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_favorites, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.favorite_name.setText(favoritesModels.get(position).getRecipe_name());
        holder.favorite_image.setImageBitmap(favoritesModels.get(position).getRecipe_image());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onTapListener != null) {
                    onTapListener.onTapView(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView favorite_name;
        ImageView favorite_image;

        public MyViewHolder(View itemView){
            super(itemView);

            favorite_image = (ImageView) itemView.findViewById(R.id.custom_favorite_image);
            favorite_name = (TextView) itemView.findViewById(R.id.custom_favorite_name);

        }
    }
    public void setOnTapListener(OnTapListener onTapListener){
        this.onTapListener = onTapListener;
    }
}
