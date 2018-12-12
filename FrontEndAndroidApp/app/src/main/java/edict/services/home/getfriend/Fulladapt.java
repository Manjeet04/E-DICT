package edict.services.home.getfriend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import edict.R;

/**
 * Created by Manjeet Singh on 7/28/2018.
 */

public class Fulladapt extends RecyclerView.Adapter<Fulladapt.Fulladaptbinviewholder> {

    LinkedHashMap<String,String> arro = new LinkedHashMap<>();
    Context o1;


    public Fulladapt(LinkedHashMap<String,String> arr, Context a1)
    {
        arro = arr;
        o1 = a1;
    }
    // 1. pehle yahan jaata hai.. layout yahin create hota hai..
    @Override
    public Fulladaptbinviewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v1  = LayoutInflater.from(o1).inflate(R.layout.fullrecycler,parent,false);

        return new Fulladaptbinviewholder(v1,arro,o1);
    }
    // ek ek mein jakar.. set krega coders.

    @Override
    public void onBindViewHolder(Fulladaptbinviewholder holder, int position) {

        String value = (new ArrayList<String>(arro.keySet())).get(position);
        // System.out.println(value);

        String key =  (new ArrayList<String>(arro.values())).get(position);

        //System.out.println(key);
        holder.t1.setText(value);
        holder.t2.setText(key);

    }

    // step 0 yahan pehle..
    @Override
    public int getItemCount() {


        return arro.size();
    }
    // 2. then yahan.. jaega..
    // task here is ... all are getting id's ( no need here basicllY.. AS NO onclicks etc. only display names )

    public class Fulladaptbinviewholder extends RecyclerView.ViewHolder
    {
        LinkedHashMap<String,String> arr = new LinkedHashMap<>();// no need yahan.. use tb hote jab...kbhi nhi
        Context rec1; // no need yahan.. use tb hote jab... kbhi nhi
        TextView t1;
        TextView t2;


        public Fulladaptbinviewholder(View itemView,LinkedHashMap<String,String> arrh,Context c1) {


            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.rectype);
            t2 = (TextView) itemView.findViewById(R.id.recinfo);
            rec1 = c1;
            arr = arrh;

        }
    }
}