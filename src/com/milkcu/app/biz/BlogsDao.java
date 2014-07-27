package com.milkcu.app.biz;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.type.TypeReference;

import com.milkcu.app.config.Constants;
import com.milkcu.app.config.Urls;
import com.milkcu.app.entity.BlogsJson;
import com.milkcu.app.entity.BlogsMoreResponse;
import com.milkcu.app.entity.BlogsResponseEntity;
import com.milkcu.app.utils.RequestCacheUtil;
import com.milkcu.app.utils.Utility;

import android.app.Activity;

public class BlogsDao extends BaseDao {

	private BlogsResponseEntity _blogsResponse;

	public BlogsDao(Activity activity) {
		super(activity);
	}

	public BlogsResponseEntity getBlogsResponse() {
		return _blogsResponse;
	}

	public void setBlogsResponse(BlogsResponseEntity blogsResponse) {
		this._blogsResponse = blogsResponse;
	}

	public BlogsResponseEntity mapperJson(boolean useCache) {
		BlogsJson blogsJson_;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					Urls.BLOGS_LIST + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, useCache);
			blogsJson_ = mObjectMapper.readValue(result,
					new TypeReference<BlogsJson>() {
					});
			if (blogsJson_ == null) {
				return null;
			}
			_blogsResponse = blogsJson_.getResponse();
			return _blogsResponse;
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

	public BlogsMoreResponse getMore(String more_url) {
		BlogsMoreResponse response;
		try {
			String result = RequestCacheUtil.getRequestContent(mActivity,
					more_url + Utility.getScreenParams(mActivity),
					Constants.WebSourceType.Json,
					Constants.DBContentType.Content_list, true);
			response = mObjectMapper.readValue(result,
					new TypeReference<BlogsMoreResponse>() {
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
