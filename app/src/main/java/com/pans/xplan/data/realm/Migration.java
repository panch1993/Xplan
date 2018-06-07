package com.pans.xplan.data.realm;

import android.support.annotation.NonNull;

import io.realm.DynamicRealm;
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

        if (oldVersion == 0L) {
            schema.get("PLAN_LIST")
                    .addField("target_time", long.class);

            oldVersion++;
        }
/*
        if (oldVersion == 2) {
            schema.get("PLAN_LIST")
                    .addField("plan_enable", boolean.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setBoolean("plan_enable",true);
                        }
                    });
            oldVersion++;
        }

        if (oldVersion == 3) {
            schema.get("PLAN_LIST")
                    .addField("modifier_date",Date.class)
                    .transform(new RealmObjectSchema.Function() {
                        @Override
                        public void apply(DynamicRealmObject obj) {
                            obj.setDate("modifier_date",obj.getDate("create_date"));
                        }
                    });
            oldVersion++;
        }

        if (oldVersion == 4) {
            schema.get("PLAN")
                    .removeField("complete_date")
                    .addRealmListField("complete_date", String.class);
            oldVersion++;
        }

        if (oldVersion == 5) {
            schema.get("PLAN")
                    .removeField("expected_completion_number")
                    .removeField("editable");
            oldVersion++;
        }

        if (oldVersion == 6) {
            RealmObjectSchema complete_times = schema.create("COMPLETE_TIMES")
                    .addField("date",int.class)
                    .addField("complete_times",String.class);
            schema.get("PLAN")
                    .removeField("complete_date")
                    .addRealmObjectField("complete_times", complete_times);
        }*/
    }
}
