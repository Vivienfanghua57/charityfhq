package com.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.entity.GoodsEntity;
import com.example.charitydemo.R;
import com.util.FormatTools;

public class GoodsAdapter extends BaseAdapter {
	LayoutInflater inflater;
	private Context context;
	private List<GoodsEntity> goods;

	public GoodsAdapter(List<GoodsEntity> goods, Context context) {
		inflater = LayoutInflater.from(context);
		this.context = context;
		this.goods = goods;
	}

	@Override
	public int getCount() {
		return goods.size();
	}

	@Override
	public Object getItem(int position) {
		return goods.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO �Զ����ɵķ������
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// String isVideo="";
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.goods_item, null);
			holder = new ViewHolder();
			holder.goods_frontimg = (ImageView) convertView
					.findViewById(R.id.goods_frontimg);
			holder.goods_type = (TextView) convertView
					.findViewById(R.id.goods_type);
			holder.goods_characters = (TextView) convertView
					.findViewById(R.id.goods_characters);
			holder.goods_descr = (TextView) convertView
					.findViewById(R.id.goods_descr);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String path = goods.get(position).getFrontimg_Path();
		Bitmap bmp = null;
		if (FormatTools.isVideo(path).equals("y")) {
			bmp = FormatTools.getBitmapsFromVideo(path);
		} else if (FormatTools.isVideo(path).equals("n")) {
			bmp = FormatTools.CompressImage(path);
		}
		// if (path.toLowerCase().endsWith(".3gp")
		// || path.toLowerCase().endsWith(".mov")
		// || path.toLowerCase().endsWith(".3gp")
		// || path.toLowerCase().endsWith(".wmv")) {
		// bmp = FormatTools.getBitmapsFromVideo(path);
		// } else if (path.toLowerCase().endsWith(".jpg")
		// || path.toLowerCase().endsWith(".jpeg")
		// || path.toLowerCase().endsWith(".png")
		// || path.toLowerCase().endsWith(".gif")) {
		// bmp = FormatTools.CompressImage(path);
		// }
		holder.goods_frontimg.setImageBitmap(bmp);
		// } catch (OutOfMemoryError err) {
		// }
		// Bitmap
		// bitmap=BitmapFactory.decodeFile(goods.get(position).getFrontimg_Path());
		// holder.goods_frontimg.setImageBitmap(bitmap);
		holder.goods_type.setText(goods.get(position).getType());
		holder.goods_characters.setText(goods.get(position).getParam1() + "  "
				+ goods.get(position).getParam2() + "  "
				+ goods.get(position).getParam3());
		holder.goods_descr.setText(goods.get(position).getDescr());
		return convertView;
	}

	class ViewHolder {
		ImageView goods_frontimg;
		TextView goods_type, goods_characters, goods_descr;
	}
}
