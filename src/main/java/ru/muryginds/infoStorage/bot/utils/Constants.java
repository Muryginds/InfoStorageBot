package ru.muryginds.infoStorage.bot.utils;

final public class Constants {

    private Constants() {}

    public static final String KEYBOARD_ADD_NOTE_OPERATED_CALLBACK = "AddingNote";
    public static final String KEYBOARD_ADD_NOTE_BUTTON_YES_COMMAND = "AddingNoteYes";
    public static final String KEYBOARD_ADD_NOTE_BUTTON_NO_COMMAND = "AddingNoteNo";
    public static final String KEYBOARD_ADD_TAG_OPERATED_CALLBACK = "AddingTags";
    public static final String KEYBOARD_ADD_TAG_BUTTON_CANCEL_COMMAND = "AddingTagsCancel";
    public static final String KEYBOARD_ADD_TAG_BUTTON_ADD_COMMAND = "AddingTagsAddNoteToDb";
    public static final String KEYBOARD_ADD_TAG_BUTTON_EDIT_COMMAND = "AddingTagsEdit";

    public static final String ASK_GET_TAGS = "Would you like to add this tags? ";
    public static final String ASK_SET_TAGS = "Please set tags for this note";
    public static final String TAGS_NOT_FOUND = "No tags found, please send again";
    public static final String ADDING_TAGS_TO_MEMORY = "Adding tags to memory";
    public static final String ADDING_MESSAGE_TO_MEMORY = "Adding this message to memory";
    public static final String ADDING_CANCELLED = "Addition is cancelled";
    public static final String ADDING_SUCCESSFUL = "Note added!";


    public static final String BOT_HELP = "This bot can store information using tags. "
            + "To start using it, send a message to bot and after add tag(s)"
            + "starting $, f.e $java $video";
    public static final String BOT_SEARCH_NOT_FOUND = "No information found";
    public static final String BOT_START = "This bot can store information!"
            + " If you want to know more, send /help";
}