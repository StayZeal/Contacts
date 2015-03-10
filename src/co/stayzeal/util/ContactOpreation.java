package co.stayzeal.util;

import java.util.ArrayList;
import java.util.List;

import co.stayzeal.contact.model.ContactInfo;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.util.Log;

/**
 * 
 * ContentResolver操作Android本地contact2数据库
 * @author ArthorK
 *
 */

public class ContactOpreation {

	private static final String TAG = "ContactOpreation";
	private Context context;
	private ContentResolver contentResolver;
	private Uri uri;
	private List<ContactInfo> contactInfos;

	public ContactOpreation(Context context) {
		this.context = context;
		contentResolver = this.context.getContentResolver();
	}

	/**
	 *获取所有联系人信息
	 */
	public List<ContactInfo> getContactsList() {

		uri = Uri.parse("content://com.android.contacts/contacts");
		Cursor idCursor = contentResolver.query(uri, new String[] { "_id" },
				null, null, null);
		contactInfos = new ArrayList<ContactInfo>();
		ContactInfo contactInfo;
		Cursor dataCursor;
		while (idCursor.moveToNext()) {
			contactInfo = new ContactInfo();
			int id = idCursor.getInt(0);
			
			
			uri = Uri.parse("content://com.android.contacts/contacts/" + id
					+ "/data");
			dataCursor = contentResolver.query(uri, new String[] {
					"data1", "mimetype" }, null, null, null);
			contactInfo.setId(id);
			//获取一个联系人的所有信息
			while (dataCursor.moveToNext()) {
				String data = dataCursor.getString(0);
				String type = dataCursor.getString(1);
				if ("vnd.android.cursor.item/name".equals(type)) {
					contactInfo.setContactName(data);
				} else if ("vnd.android.cursor.item/phone_v2".equals(type)) {
					contactInfo.setContactNumber(data);
				} else if ("vnd.android.cursor.item/email_v2".equals(type)) {
					contactInfo.setEamil(data);
				}
			}
			contactInfos.add(contactInfo);
			dataCursor.close();
		}
		idCursor.close();
		return contactInfos;
	}
	
	/**
	 * ͨ同过手机号码获取联系人信息
	 * @param phoneNumber
	 * @return
	 */
	public ContactInfo getContactByPhone(String phoneNumber) {
		
		ContactInfo contactInfo=new ContactInfo();
		uri = Uri.parse("content://com.android.contacts/data/phones/filter/"+phoneNumber);
		Cursor c = contentResolver.query(uri, new String[] { "display_name" },
				null, null, null);
		while (c.moveToNext()) {
			contactInfo.setContactName(c.getString(0));
			//System.out.println("getContactByPhone(): "+c.getString(0));
		}
		
		return contactInfo;
	}
	
	
	/**
	 * 添加联系人
	 * @param contactInfo
	 */
	public void addContact(ContactInfo contactInfo){
		
		uri=Uri.parse("content://com.android.contacts/raw_contacts");  
		ContentValues values = new ContentValues();  
		// ��raw_contacts����һ������ID֮��, ����ȫ��ΪNULL�ļ�¼, ID���Զ���ɵ�  
		long id = ContentUris.parseId(contentResolver.insert(uri, values));   
		//�����ϵ������  
		uri = Uri.parse("content://com.android.contacts/data");  
		values.put("raw_contact_id", id);  
		values.put("data2", contactInfo.getContactName());  
		values.put("mimetype", "vnd.android.cursor.item/name");  
		contentResolver.insert(uri, values);  
		                //�����ϵ�˵绰  
		values.clear(); // ����ϴε����  
		values.put("raw_contact_id", id);  
		values.put("data1", contactInfo.getContactNumber());  
		values.put("data2", "2");  
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");  
		contentResolver.insert(uri, values);  
		//�����ϵ������  
		values.clear();  
		values.put("raw_contact_id", id);  
		values.put("data1", contactInfo.getEamil());  
		values.put("data2", "1");  
		values.put("mimetype", "vnd.android.cursor.item/email_v2");  
		contentResolver.insert(uri, values); 
	}
	
	/**
	 *通过transaction添加联系人
	 * @param contactInfo
	 * @throws RemoteException
	 * @throws OperationApplicationException
	 */
	public void addContact2(ContactInfo contactInfo) throws RemoteException, OperationApplicationException{
		  
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        int rawContactInsertIndex = 0;
        ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
                .withValue(RawContacts.ACCOUNT_TYPE, null)
                .withValue(RawContacts.ACCOUNT_NAME, null)
                .build());
        
        //文档位置：reference\android\provider\ContactsContract.Data.html
        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
                .withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
                .withValue(StructuredName.GIVEN_NAME, "lisi")
                .build());
        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
                 .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
                 .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
                 .withValue(Phone.NUMBER, "5556")
                 .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
                 .withValue(Phone.LABEL, "")
                 .build());
        ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
                 .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
                 .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
                 .withValue(Email.DATA, "lisi@126.cn")
                 .withValue(Email.TYPE, Email.TYPE_WORK)
                 .build());
        
        ContentProviderResult[] results = contentResolver.applyBatch(ContactsContract.AUTHORITY,ops);
        for (ContentProviderResult result : results) {
            Log.i(TAG, result.uri.toString());
        }
   	
	}
	
	public void deleteByPhone(String phone){
		Uri url=null;
		String where=null;
		String[] selectionArgs=null;
		contentResolver.delete(url, where, selectionArgs);
	}
	
}
