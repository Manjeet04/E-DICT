package edict.services.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import edict.R;
import edict.models.MostSearchModel;
import edict.models.MostSearchModel;
import edict.services.home.getfriend.FullFriendInfo;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.AlphalistBindViewHolder>{
    List<MostSearchModel> list;
    Context context;
    public HomeListAdapter(List<MostSearchModel> res,Context c1) { // 1
        list = res;
        context = c1;
    }

    @Override
    public AlphalistBindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { // 3
        View view = LayoutInflater.from(context)
                .inflate(R.layout.home_layout,parent,false);
        return new AlphalistBindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlphalistBindViewHolder holder, int position) {  // 5

        if(list.get(position).getWord()!=null) {
            holder.word.setText("" + list.get(position).getWord());

        }
        if(list.get(position).getMeaning()!=null) { // correct here.
            holder.meaning.setText(""+ list.get(position).getMeaning());
        }
        if(list.get(position).getSynonym()!=null) {
            holder.synonym.setText("" + list.get(position).getSynonym());
        }
        if(list.get(position).getAntonym()!=null) {
            holder.antonym.setText("" + list.get(position).getAntonym());
        }
        if(list.get(position).getWord_origin()!=null) {
            holder.wordorigin.setText("" + list.get(position).getWord_origin());
        }
        if(list.get(position).getExample()!=null) {
            holder.example.setText("" + list.get(position).getExample());
        }
        if(list.get(position).getHit_points()!=0) {
            holder.hitscore.setVisibility(View.VISIBLE);
            holder.hitscore.setText("" + list.get(position).getHit_points());
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    } // 2

    public class AlphalistBindViewHolder extends RecyclerView.ViewHolder{ // 4

        TextView word;
        TextView meaning;
        TextView synonym;
        TextView antonym;
        TextView wordorigin;
        TextView example;
        TextView hitscore;


        public AlphalistBindViewHolder(View itemView) {
            super(itemView);

            word = (TextView) itemView.findViewById(R.id.word);
            meaning = (TextView) itemView.findViewById(R.id.meaning);
            synonym = (TextView) itemView.findViewById(R.id.synonym);
            antonym = (TextView) itemView.findViewById(R.id.antonym);
            wordorigin = (TextView) itemView.findViewById(R.id.wordorigin);
            example = (TextView) itemView.findViewById(R.id.example);
            hitscore = (TextView) itemView.findViewById(R.id.hitscore);


        }
    }
}
