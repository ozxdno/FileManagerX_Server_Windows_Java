package com.FileManagerX.BasicCollections;

public class Errors extends BasicCollection<com.FileManagerX.BasicModels.Error> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public final static com.FileManagerX.Interfaces.ICollection.IKey KeyForType =
		new com.FileManagerX.Interfaces.ICollection.IKey() {
			public Object getKey(Object item) {
				if(item instanceof com.FileManagerX.BasicModels.Error) {
					com.FileManagerX.BasicModels.Error i = (com.FileManagerX.BasicModels.Error)item;
					return i.getType();
				}
				return null;
			}
		};
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Errors() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLinkedList<>());
		this.setKey(KeyForType);
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Errors searchByTimeBefore(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Error> it = this.getIterator();
		Errors errors = new Errors();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Error e = it.getNext();
			if(e.getTime() < time) {
				errors.add(e);
			}
		}
		return errors;
	}
	public Errors fetchByTimeBefore(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Error> it = this.getIterator();
		Errors errors = new Errors();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Error e = it.getNext();
			if(e.getTime() < time) {
				it.remove();
				errors.add(e);
			}
		}
		return errors;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Errors searchByTimeBehind(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Error> it = this.getIterator();
		Errors errors = new Errors();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Error e = it.getNext();
			if(e.getTime() > time) {
				errors.add(e);
			}
		}
		return errors;
	}
	public Errors fetchByTimeBehind(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Error> it = this.getIterator();
		Errors errors = new Errors();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Error e = it.getNext();
			if(e.getTime() > time) {
				it.remove();
				errors.add(e);
			}
		}
		return errors;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public boolean save() {
		int amount = this.size();
		boolean ok = true;
		
		for(int i=0; i<amount; i++) {
			com.FileManagerX.BasicModels.Error e = this.fetchByCount(0);
			String url = com.FileManagerX.Tools.Pathes.LOG.getAbsolute() + "\\" + e.getShortTime_Date() + ".log";
			java.io.File log = new java.io.File(url);
			try {
				if(!log.exists()) { log.createNewFile(); }
				java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(log, true));
				bw.write(e.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception exception) {
				exception.printStackTrace();
				ok = false;
				continue;
			}
		}
		
		return ok;
	}
	public boolean deleteAgoLogs(int permitLogAmount) {
		boolean ok = true;
		
		java.io.File logFolder = new java.io.File(com.FileManagerX.Tools.Pathes.LOG.getAbsolute());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(com.FileManagerX.Tools.Url.getExtension(f.getName()).equals(".log")) {
				long t = com.FileManagerX.Tools.Time.shortTimeDate2Ticks
						(com.FileManagerX.Tools.Url.getNameWithoutExtension(f.getName()));
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitLogAmount) {
			java.util.Collections.sort(time);
			for(int i=permitLogAmount; i<time.size(); i++) {
				String url = com.FileManagerX.Tools.Pathes.LOG.getAbsolute() + "\\" +
						com.FileManagerX.Tools.Time.ticks2ShortTime_Date(time.get(i)) + ".log";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register
						("Delete Log File Failed", e.toString());
					ok = false;
				}
			}
		}
		return ok;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
