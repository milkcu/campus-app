package com.milkcu.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.milkcu.app.config.Constants;
import com.milkcu.app.entity.DetailJson;
import com.milkcu.app.entity.DetailResponseEntity;
import com.milkcu.app.utils.RequestCacheUtil;
import com.milkcu.app.utils.Utility;

import android.app.Activity;
import android.util.Log;

public class DetailDao extends BaseDao {
	
	private String mUrl;
	
	public DetailDao(Activity activity,String url)
	{
		super(activity);
		mUrl=url+ Utility.getScreenParams(mActivity);
	}
	
	private DetailResponseEntity mDetailResponseEntity;

	public DetailResponseEntity getmDetailResponseEntity() {
		return mDetailResponseEntity;
	}

	public void setmDetailResponseEntity(DetailResponseEntity mDetailResponseEntity) {
		this.mDetailResponseEntity = mDetailResponseEntity;
	}
	
	public DetailResponseEntity mapperJson(boolean useCache){
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					mUrl, Constants.WebSourceType.Json,
					Constants.DBContentType.Content_content, useCache);
			Log.i("info",mUrl);
			DetailJson detailJson = mObjectMapper.readValue(result, new TypeReference<DetailJson>() {});
			this.mDetailResponseEntity = detailJson.getResponse();
			return this.mDetailResponseEntity;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
