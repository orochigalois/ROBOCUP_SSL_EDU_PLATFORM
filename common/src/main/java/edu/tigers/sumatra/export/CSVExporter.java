/*
* *********************************************************
* Copyright (c) 2017 智动天地(北京)科技有限公司
* All rights reserved.
* Project: 标准平台决策开发系统
* Authors:
* 智动天地(北京)科技有限公司
* *********************************************************
*/

package edu.tigers.sumatra.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

import org.apache.log4j.Logger;



public final class CSVExporter
{
	// --------------------------------------------------------------------------
	// --- variables and constants ----------------------------------------------
	// --------------------------------------------------------------------------
	
	private static final Logger	log				= Logger.getLogger(CSVExporter.class.getName());
	private boolean					autoIncrement	= false;
	private final String				fileName;
	private final Queue<String>	values			= new LinkedList<String>();
	private final Queue<String>	header			= new LinkedList<String>();
	private String						additionalInfo	= "";
	private File						file;
	private BufferedWriter			fileWriter;
	private boolean					writeHeader		= false;
	private static final String	delimiter		= ",";
	private int							headerSize		= 0;
	
	private boolean					isClosed			= false;
	private boolean					append			= false;
	
	
	
	public CSVExporter(final String fileName, final boolean autoIncrement, final boolean append)
	{
		this.fileName = fileName;
		this.autoIncrement = autoIncrement;
		this.append = append;
	}
	
	
	
	public CSVExporter(final String fileName, final boolean autoIncrement)
	{
		this(fileName, autoIncrement, false);
	}
	
	
	
	public static void exportList(final String folder, final String key, final Stream<INumberListable> stream)
	{
		CSVExporter exporter = new CSVExporter(folder + "/" + key, false);
		stream.forEach(nl -> exporter.addValues(nl.getNumberList()));
		exporter.close();
	}
	
	
	
	public void addValues(final Number... values)
	{
		this.values.clear();
		for (final Number f : values)
		{
			this.values.add(String.valueOf(f));
		}
		persistRecord();
	}
	
	
	
	public void addValues(final List<Number> values)
	{
		this.values.clear();
		for (final Object f : values)
		{
			this.values.add(String.valueOf(f));
		}
		persistRecord();
	}
	
	
	
	public void addValuesBean(final Object bean) throws IllegalAccessException
	{
		values.clear();
		
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			values.add(String.valueOf(field.get(bean)));
		}
		persistRecord();
	}
	
	
	
	public void setHeader(final String... header)
	{
		this.header.clear();
		for (final String string : header)
		{
			this.header.add(string);
		}
		writeHeader = true;
		headerSize = this.header.size();
	}
	
	
	
	public void setHeaderBean(final Object bean)
	{
		header.clear();
		
		Field[] fields = bean.getClass().getDeclaredFields();
		for (Field field : fields)
		{
			header.add(field.getName());
		}
		
		writeHeader = true;
		headerSize = header.size();
	}
	
	
	
	public void setAdditionalInfo(final String info)
	{
		additionalInfo = info;
	}
	
	
	
	private void persistRecord()
	{
		try
		{
			if (file == null)
			{
				File dir = new File(fileName).getParentFile();
				if (!dir.exists())
				{
					boolean created = dir.mkdirs();
					if (!created)
					{
						log.warn("Could not create export dir: " + dir.getAbsolutePath());
					}
				}
				int counter = 0;
				if (autoIncrement)
				{
					while ((file = new File(fileName + counter + ".csv")).exists())
					{
						counter++;
					}
				} else
				{
					file = new File(fileName + ".csv");
				}
				
				fileWriter = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(file, append), "UTF-8"));
				
				if (writeHeader)
				{
					fileWriter.write("#Sumatra CSVExporter\n");
					fileWriter.write("#" + new Date().toString() + "\n");
					
					if (!additionalInfo.isEmpty())
					{
						fileWriter.write("#" + additionalInfo + "\n");
					}
					
					fileWriter.write(header.poll());
					for (final String s : header)
					{
						fileWriter.write(delimiter + s);
					}
					fileWriter.write("\n");
					fileWriter.flush();
				}
				
			}
			if (writeHeader && (headerSize != values.size()))
			{
				throw new CSVExporterException("object count on values must match header", null);
			}
			fileWriter.write(values.poll());
			for (final String s : values)
			{
				fileWriter.write(delimiter + s);
			}
			fileWriter.write("\n");
			fileWriter.flush();
			
		} catch (final FileNotFoundException err)
		{
			throw new CSVExporterException("file not found", err);
		} catch (final IOException err)
		{
			throw new CSVExporterException("io error", err);
		}
		
	}
	
	
	
	public String getAbsoluteFileName()
	{
		return new File(fileName + ".csv").getAbsolutePath();
	}
	
	
	
	public void close()
	{
		if (fileWriter != null)
		{
			try
			{
				fileWriter.close();
				log.debug("Saved csv file to " + file.getAbsolutePath());
			} catch (final IOException err)
			{
				throw new CSVExporterException("io error while closing the file", err);
			}
		}
		isClosed = true;
	}
	
	
	
	public boolean isClosed()
	{
		return isClosed;
	}
	
	
	
	public boolean isEmpty()
	{
		return values.isEmpty();
	}
	
	// --------------------------------------------------------------------------
	// --- getter/setter --------------------------------------------------------
	// --------------------------------------------------------------------------
}
