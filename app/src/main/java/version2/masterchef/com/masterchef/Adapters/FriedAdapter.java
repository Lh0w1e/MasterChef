package version2.masterchef.com.masterchef.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import version2.masterchef.com.masterchef.Models.FriedModel;
import version2.masterchef.com.masterchef.Models.StewModel;
import version2.masterchef.com.masterchef.R;

/**
 * Created by Colinares on 10/3/2017.
 */
public class FriedAdapter extends BaseAdapter{
    private Context mContext;
    private List<FriedModel> mRecipeList;
    private int layout;

    public FriedAdapter(Context mContext, int layout, List<FriedModel> mRecipeList) {
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView txtRecipeName;
        ImageView RecipeImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.txtRecipeName = (TextView) row.findViewById(R.id.custom_fried_name);
            holder.RecipeImage = (ImageView) row.findViewById(R.id.custom_fried_image);

            row.setTag(holder);

        }else{

            holder = (ViewHolder) row.getTag();
        }

        try{

            FriedModel recipesModel = mRecipeList.get(position);

            holder.txtRecipeName.setText(recipesModel.getRecipe_name());
            holder.RecipeImage.setImageBitmap(recipesModel.getRecipe_image());

        }catch (RuntimeException e){
            e.printStackTrace();
        }

        return row;
    }
}
