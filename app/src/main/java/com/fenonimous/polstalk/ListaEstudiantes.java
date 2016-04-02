package com.fenonimous.polstalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenonimous.polstalk.custom.CustomActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ListaEstudiantes is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class ListaEstudiantes extends CustomActivity
{


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_estudiantes);
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}


	/**
	 * Cargar lista de estudiantes
	 */
	private void loadStudentsList()
	{

	}

	/**
	 * Clase adaptadora que maneja los elementos que se tienen en
	 * el listview de estudiantes.
	 */
	private class StudentAdapter extends ArrayAdapter
	{

		public StudentAdapter(Context context, int resource, List objects) {
			super(context, resource, objects);
		}

		@Override
		public View getView(int pos, View v, ViewGroup arg2)
		{
			return null;
		}

	}
}
