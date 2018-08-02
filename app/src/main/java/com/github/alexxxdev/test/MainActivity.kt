package com.github.alexxxdev.test

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_main.*
import io.realm.RealmResults
import io.realm.OrderedRealmCollectionChangeListener
import io.realm.Realm


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        val dog = Dog()
        dog.name = "Rex"
        dog.age = 1

        Realm.init(applicationContext)


        val realm = Realm.getDefaultInstance()


        val puppies = realm.where(Dog::class.java).lessThan("age", 2).findAll()
        puppies.size

        realm.beginTransaction()
        val managedDog = realm.copyToRealm(dog)
        val person = realm.createObject(Person::class.java)
        person.dogs?.add(managedDog)
        realm.commitTransaction()


        puppies.addChangeListener(OrderedRealmCollectionChangeListener<RealmResults<Dog>> { results, changeSet ->
        })

        realm.executeTransactionAsync(object : Realm.Transaction {
            override fun execute(bgRealm: Realm) {
                val dog = bgRealm.where(Dog::class.java).equalTo("age", 1.toInt()).findFirst()
                dog?.age = 3
            }
        }, object : Realm.Transaction.OnSuccess {
            override fun onSuccess() {
                puppies.size
                managedDog.age
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
