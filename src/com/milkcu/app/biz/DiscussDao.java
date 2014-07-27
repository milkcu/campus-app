package com.milkcu.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.milkcu.app.config.Constants;
import com.milkcu.app.entity.DetailsOwnDiscussJson;
import com.milkcu.app.entity.NewsResponseEntity;
import com.milkcu.app.utils.RequestCacheUtil;

import android.app.Activity;

public class DiscussDao extends BaseDao {

	public DiscussDao(Activity activity) {
		super(activity);
	}

	private NewsResponseEntity _newsResponse;

	public NewsResponseEntity get_newsResponse() {
		return _newsResponse;
	}

	public void set_newsResponse(NewsResponseEntity _newsResponse) {
		this._newsResponse = _newsResponse;
	}

	public DetailsOwnDiscussJson mapperJson(String url, boolean useCache) {
		// TODO Auto-generated method stub
		DetailsOwnDiscussJson json = null;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity, url,
					Constants.WebSourceType.Json,
					Constants.DBContentType.Discuss, useCache);
			json = mObjectMapper.readValue(result,
					new TypeReference<DetailsOwnDiscussJson>() {
					});
			return json;
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
