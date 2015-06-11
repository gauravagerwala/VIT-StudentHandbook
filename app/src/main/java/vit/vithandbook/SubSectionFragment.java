package vit.vithandbook;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.Inflater;


public class SubSectionFragment extends BackHandlerFragment {

    public  String mainCategory ;
    ArrayList<String> Subtopics ;

    public static SubSectionFragment newInstance(String mainCategory)
    {
        SubSectionFragment frag = new SubSectionFragment() ;
        frag.mainCategory = mainCategory ;
        return frag ;
    }
    public SubSectionFragment()
    {

    }

    @Override
    public View onCreateView (LayoutInflater inflater , ViewGroup container , Bundle SavedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_sub_section,container,false);
        Subtopics = new ArrayList<String>();
        Subtopics.add("Hostel Fees");
        Subtopics.add("Mess Refund");
        ListView list =(ListView) view.findViewById(R.id.subSectionList);
        list.setAdapter(new SubSectionAdapter(getActivity(), R.layout.sub_section_item, Subtopics));
        ((ArrayAdapter)list.getAdapter()).notifyDataSetChanged();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // to navigate to sub sub level
            }
        });
        return view ;
    }
    @Override
    public boolean onBackPressed()
    {

        return false ;
    }

}


class SubSectionAdapter extends ArrayAdapter<String>
{
    Context activity ;
    int [] colors ;
    ArrayList<String> objects;
    class ViewHolder
    {
       public TextView content , circle  ;
    }
    public SubSectionAdapter(Context context, int resource, ArrayList<String> objects)
    {
        super(context, resource, objects);
        activity = context ;
        this.objects = objects ;
        colors = activity.getResources().getIntArray(R.array.colors);
    }
    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder ;
        if(view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.sub_section_item, parent, false);
            holder = new ViewHolder();
            holder.content = (TextView) view.findViewById(R.id.tvContent);
            holder.circle = (TextView) view.findViewById(R.id.tvCircle);
            view.setTag(holder)
            ;
        }
        else
            holder = (ViewHolder)view.getTag();
        Random r = new Random();
        int nextindex = r.nextInt(3);
        holder.content.setText(objects.get(position));
        ((GradientDrawable)holder.circle.getBackground()).setColor(colors[nextindex]);
        holder.circle.setText(String.valueOf(objects.get(position).charAt(0)));
        return view ;
    }

}
