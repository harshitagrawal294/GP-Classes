package com.harshit.dbms.Utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SetParameters {
	
	public static void setParameters(PreparedStatement statement, Object... parameters) throws SQLException {
		
		
		for (int i=0; i < parameters.length; i++) {
			statement.setObject(i+1, parameters[i]);
			
		}
	}
	

}
