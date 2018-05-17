package com.pans.xplan.data.realm;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;

import java.util.Date;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * @author android01
 * @date 2018/5/17.
 * @time 下午3:19.
 */
public class Migration implements RealmMigration {
    @Override
    public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();

        if (oldVersion == 1L) {
            schema.create("PLAN_LIST")
                    .addField("primary_key",String.class, FieldAttribute.PRIMARY_KEY)
                    .addField("plan_type",String.class)
                    .addField("plan_title",String.class)
                    .addField("plan_describe",String.class)
                    .addField("create_date",Date.class)
                    .addField("editable",boolean.class)
                    .addField("expected_completion_number",int.class)
                    .addField("repeat_type",String.class)
                    .addField("repeat_days",byte[].class);

            schema.get("PLAN")
                    .removeField("repeat_type");

            oldVersion++;
        }


    }
}
