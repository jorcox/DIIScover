package nemesis;

import nemesis.BD.Cursor;

/**
 * Created by inigo on 26/07/2015.
 */
public interface AsyncResponse {
    void processFinish(Cursor cursor,String output);
}