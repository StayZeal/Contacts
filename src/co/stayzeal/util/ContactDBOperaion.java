package co.stayzeal.util;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import co.stayzeal.contact.model.ContactInfo;

/**
 * 
 * ContactOperation改进版本。
 * 1.添加了按联系人排序的功能
 * 发现：加载速度比ContactOperation.class 速度快。
 * @author ArthorK
 *
 */
public class ContactDBOperaion {

	private ContentResolver contentResolver;

	public ContactDBOperaion(Context context) {
		this.contentResolver = context.getContentResolver();
	}

	public List<ContactInfo> getContactsList() {

		List<ContactInfo> contactInfos = new ArrayList<ContactInfo>();
		Cursor cursor = null;
		try {
			Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
			// 这里是获取联系人表的电话里的信息 包括：名字，名字拼音，联系人id,电话号码；
			// 然后在根据"sort-key"排序
			cursor = contentResolver
					.query(uri, new String[] { "display_name", "sort_key",
							"contact_id", "data1" }, null, null, "sort_key");
			if (cursor.moveToFirst()) {
				do {
					ContactInfo contact = new ContactInfo();
					contact.setId(cursor.getInt(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)));
					contact.setContactName(cursor.getString(0));
					contact.setContactNumber(cursor.getString(cursor
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					contact.setSortKey(getSortKey(cursor.getString(1)));
					contactInfos.add(contact);
				} while (cursor.moveToNext());
			}
		} catch (Exception e) {

		} finally {//使用之后要关闭
			cursor.close();
		}

		return contactInfos;
	}
	
	/**
     * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
     * @param sortKeyString
     * 数据库中读取出的sort key
     * @return 英文字母或者#
     */
    @SuppressLint("DefaultLocale")
	private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }
    
    /**
     * 删除联系人
     * @return
     */
    public boolean deleteContacts(){
    	
    	return true;
    }
}
