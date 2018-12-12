package edict.services.home.getfriend;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import edict.R;
import edict.models.WordModel;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public class PhraseSearchAdapter extends RecyclerView.Adapter<PhraseSearchAdapter.PhraseSearchAdapterBindViewHolder>{

    Context context;
    List<WordModel> list;

    public PhraseSearchAdapter(Context co, List<WordModel> li) {
        context = co;
        list = li;
    }

    @Override
    public PhraseSearchAdapterBindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.home_layout,parent,false);
        return new PhraseSearchAdapterBindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhraseSearchAdapterBindViewHolder holder, int position) {
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
        if(list.get(position).getHit_points()!=null && list.get(position).getHit_points()!=0) {
            holder.hitscore.setText("" + list.get(position).getHit_points());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhraseSearchAdapterBindViewHolder extends RecyclerView.ViewHolder {
        TextView word;
        TextView meaning;
        TextView synonym;
        TextView antonym;
        TextView wordorigin;
        TextView example;
        TextView hitscore;
        public PhraseSearchAdapterBindViewHolder(View view) {
            super(view);

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
