package ch.zli.pg.app.data;

import java.util.ArrayList;
import java.util.List;

public class ContactModel {

    private static ContactModel model;

    private final List<Contact> contacts;

    private ContactModel() {
        contacts = new ArrayList<>();
    }

    private static ContactModel getModel() {
        if (model == null)
            model = new ContactModel();
        return model;
    }

    public static void addContact(Contact contact) {
        getModel().contacts.add(contact);
    }

    public static List<Contact> getContacts() {
        return getModel().contacts;
    }

}
