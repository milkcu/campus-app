package com.milkcu.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.milkcu.app.config.Constants;
import com.milkcu.app.config.Urls;
import com.milkcu.app.entity.WikiJson;
import com.milkcu.app.entity.WikiMoreResponse;
import com.milkcu.app.entity.WikiResponseEntity;
import com.milkcu.app.utils.RequestCacheUtil;
import com.milkcu.app.utils.Utility;

import android.app.Activity;

import android.util.Log;

public class WikiDao extends BaseDao {

	public WikiDao(Activity activity) {
		super(activity);
	}

	private WikiResponseEntity mWikiResponseEntity;

	public WikiResponseEntity getmWikiResponseEntity() {
		return mWikiResponseEntity;
	}

	public void setmWikiResponseEntity(WikiResponseEntity mWikiResponseEntity) {
		this.mWikiResponseEntity = mWikiResponseEntity;
	}

	public WikiResponseEntity mapperJson(boolean useCache, int sid) {
		// TODO Auto-generated method stub
		WikiJson wikiJson;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					//"http://api.eoe.cn/client/wiki?k=lists",
					Urls.BASEURL+"/app/?view=list&sid="+sid, 
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			Log.v("test", Urls.BASEURL+"/app/?view=list&sid="+sid);
			wikiJson = mObjectMapper.readValue(result,
					new TypeReference<WikiJson>() {
					});
			if (wikiJson == null) {
				return null;
			}
			this.mWikiResponseEntity = wikiJson.getResponse();
			//Log.v("test", mWikiResponseEntity);
			return mWikiResponseEntity;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public WikiMoreResponse getMore(String more_url) {
		WikiMoreResponse response;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					more_url + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, true);
			response = mObjectMapper.readValue(result,
					new TypeReference<WikiMoreResponse>() {
					});
			return response;
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
