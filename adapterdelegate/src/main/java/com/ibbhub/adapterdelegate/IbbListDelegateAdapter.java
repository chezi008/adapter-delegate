package com.ibbhub.adapterdelegate;

import java.util.List;

/**
 * @author ：chezi008 on 2018/8/18 10:09
 * @description ：The fallback delegate adapter
 * @email ：chezi008@163.com
 */
public class IbbListDelegateAdapter<T extends List<?>> extends ListDelegationAdapter<T> {

    public void setListener(AdapterListener<T> listener) {
        if (delegatesManager.getFallbackDelegate() != null) {
            delegatesManager.getFallbackDelegate().setListener(listener);
        }
    }
}
