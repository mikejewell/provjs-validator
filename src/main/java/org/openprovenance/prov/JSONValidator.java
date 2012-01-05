package org.openprovenance.prov;

import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import eu.vahlas.json.schema.JSONSchema;
import eu.vahlas.json.schema.JSONSchemaProvider;
import eu.vahlas.json.schema.impl.JacksonSchemaProvider;

public class JSONValidator {

	public JSONValidator()
	{

	}

	public List<String> validate(String file) throws Exception
	{
		ObjectMapper mapper = new ObjectMapper();
		JSONSchemaProvider schemaProvider = new JacksonSchemaProvider(mapper);
		JSONSchema schema = schemaProvider
				.getSchema(new URL(
						"https://raw.github.com/trungdong/w3-prov/master/specs/json/prov-json-schema.js"));

		return schema.validate(new URL(file));
	}

	public static void main(String args[]) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println("Usage: proval <url>");
			System.exit(1);
		}

		try
		{
			JSONValidator validator = new JSONValidator();
			List<String> errors = validator.validate(args[0]);
			if (errors.size() == 0)
			{
				System.out.println("JSON is compliant with the PROV-JS Schema");
			} else
			{
				System.out.println(errors.size() + " validation error(s):");
				for (String error : errors)
				{
					System.out.println(error);
				}
			}
		} catch (Exception e)
		{
			System.err.println("Parsing failed: " + e.getMessage());
		}
	}
}
