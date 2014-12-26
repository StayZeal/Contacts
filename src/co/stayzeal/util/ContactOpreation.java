package co.stayzeal.util;

import java.util.ArrayList;
import java.util.List;

import co.stayzeal.contact.model.ContactInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * ͨ��ContentResolver��Android ϵͳ�е�contact�е����ݽ��в���
 * @author YOUNG
 *
 */
public class ContactOpreation {
	
	private Context context;
	private Uri uri;
	private List<ContactInfo> contactInfos; 
	
	public ContactOpreation(Context context) {
		this.context=context;
	}
	
	/**
	 * ��ȡ���е���ϵ����Ϣ
	 */
	public List<ContactInfo> getContactList(){
		
		ContentResolver contentResolver=context.getContentResolver();
		uri = Uri.parse("content://com.android.contacts/contacts"); 
		Cursor idCursor=contentResolver.query(uri,new String[]{"_id"}, null, null, null);
		contactInfos=new ArrayList<ContactInfo>();
		ContactInfo contactInfo;
	    while(idCursor.moveToNext()){
	    	contactInfo=new ContactInfo();
	    	int id=idCursor.getInt(0);
	    	uri = Uri.parse("content://com.android.contacts/contacts/" + id + "/data");  
	        Cursor dataCursor = contentResolver.query(uri, new String[] { "data1", "mimetype" }, null, null, null);  
	        contactInfo.setId(id);
	        //��ѯ��ϵ�˱��е�  
	        while (dataCursor.moveToNext()) {  
	            String data = dataCursor.getString(0);  
	            String type = dataCursor.getString(1);  
	            if ("vnd.android.cursor.item/name".equals(type))  {
	            	contactInfo.setContactName(data);
	            }
	            else if ("vnd.android.cursor.item/phone_v2".equals(type)){
	            	contactInfo.setContactNumber(data);
	            }
	            else if ("vnd.android.cursor.item/email_v2".equals(type)){
	            	contactInfo.setEamil(data);
	            }
	        }  
	        contactInfos.add(contactInfo);
	    }
	    return contactInfos;
	}
}
