package com.mozilla.hackathon.kiboko.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.mozilla.hackathon.kiboko.Analytics;
import com.mozilla.hackathon.kiboko.R;
import com.mozilla.hackathon.kiboko.models.IconTopic;

import java.util.ArrayList;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class IconsAdapter extends BaseAdapter implements Filterable {
    List<IconTopic> topics;
    private Context context;
    private Filter topicFilter;
    private List<IconTopic> origTopicList;

    private int analyticsStartClicks = 0;
    private final int ANALYTICS_CLICKS = 10;

    public IconsAdapter(Context ctx, List<IconTopic> topics) {
        this.topics = topics;
        this.context = ctx;
        this.origTopicList = topics;
    }

    @Override
    public int getCount() {
        return topics.size();
    }

    @Override
    public IconTopic getItem(int position) {
        return topics.get(position);
    }

    @Override
    public long getItemId(int position) {
        return topics.get(position).hashCode();
    }

    /* *********************************
     * We use the holder pattern
	 * It makes the view faster and avoid finding the component
	 * **********************************/

    private static class Holder {
        ImageView img;
    }

    public void resetData() {
        topics = origTopicList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View viewItem = convertView;

        Holder holder = new Holder();

        // First let's verify the convertView is not null
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewItem = inflater.inflate(R.layout.icons_grid_item, null);
            // Now we can fill the layout with the right values
            ImageView imageView = (ImageView) viewItem.findViewById(R.id.dashboard_icon);

            holder.img = imageView;

            viewItem.setTag(holder);
        } else
            holder = (Holder) viewItem.getTag();

        final IconTopic topic = topics.get(position);
        holder.img.setImageResource(topic.getImage());

        viewItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (analyticsStartClicks > ANALYTICS_CLICKS) {
                    Analytics.shareAnalytics();
                    return true;
                } else {
                    return false;
                }
            }
        });

        viewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SimpleTooltip tooltip = new SimpleTooltip.Builder(context)
                        .anchorView(v)
                        .text(topic.getDescription())
                        .gravity(Gravity.BOTTOM)
                        .dismissOnOutsideTouch(true)
                        .dismissOnInsideTouch(false)
                        .modal(false)
                        .animated(false)
                        .contentView(R.layout.tooltip_dso, R.id.tv_text)
                        .build();

                if (topic.getTag().equals("wifi")) {
                    ++analyticsStartClicks;
                } else {
                    analyticsStartClicks = 0;
                }

                TextView text = tooltip.findViewById(R.id.tv_title);
                text.setText(topic.getTitle());

//                tooltip.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v2) {
//                        if (tooltip.isShowing())
//                            tooltip.dismiss();
//
//                        Intent topicIntent = new Intent(context, TutorialSlideActivity.class);
//                        topicIntent.putExtra("title", topic.getDescription());
//                        topicIntent.putExtra("topic", topic.getTag());
//                        context.startActivity(topicIntent);
//                    }
//                });
                tooltip.show();
                Analytics.add("Icon List icon clicked", topic.getTag());
            }
        });

        return viewItem;
    }

    /*
	 * We create our filter
	 */

    @Override
    public Filter getFilter() {
        if (topicFilter == null)
            topicFilter = new TopicsFilter();

        return topicFilter;
    }


    private class TopicsFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            // We implement here the filter logic
            if (constraint == null || constraint.length() == 0) {
                // No filter implemented we return all the list
                results.values = origTopicList;
                results.count = origTopicList.size();
            } else {
                // We perform filtering operation
                List<IconTopic> nTopicList = new ArrayList<IconTopic>();

                for (IconTopic topic : topics) {
                    if (topic.getDescription().toUpperCase().startsWith(constraint.toString().toUpperCase()))
                        nTopicList.add(topic);
                }

                results.values = nTopicList;
                results.count = nTopicList.size();

            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {

            // Now we have to inform the adapter about the new list filtered
            if (results.count == 0)
                notifyDataSetInvalidated();
            else {
                topics = (List<IconTopic>) results.values;
                notifyDataSetChanged();
            }

        }

    }

}