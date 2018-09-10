package com.FileManagerX.BasicCollections;

public class Records extends BasicCollection<com.FileManagerX.BasicModels.Record> {

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Records() {
		this.initThis();
	}
	private void initThis() {
		this.setContent(new com.FileManagerX.BasicCollections.BasicLinkedList<>());
		this.setKey(new KeyForType());
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public com.FileManagerX.BasicModels.Record createT() {
		return new com.FileManagerX.BasicModels.Record();
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Records searchByTimeBefore(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		Records records = new Records();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Record e = it.getNext();
			if(e.getTime() < time) {
				records.add(e);
			}
		}
		return records;
	}
	public Records fetchByTimeBefore(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		Records records = new Records();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Record e = it.getNext();
			if(e.getTime() < time) {
				it.remove();
				records.add(e);
			}
		}
		return records;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public Records searchByTimeBehind(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		Records records = new Records();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Record e = it.getNext();
			if(e.getTime() > time) {
				records.add(e);
			}
		}
		return records;
	}
	public Records fetchByTimeBehind(long time) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		Records records = new Records();
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Record e = it.getNext();
			if(e.getTime() > time) {
				it.remove();
				records.add(e);
			}
		}
		return records;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public boolean save() {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		boolean ok = true;
		while(it.hasNext()) {
			com.FileManagerX.BasicModels.Record r = it.getNext();
			String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + "\\" +
					com.FileManagerX.Tools.Time.ticks2ShortTime_Date(r.getTime()) + ".rec";
			java.io.File rec = new java.io.File(url);
			try {
				if(!rec.exists()) { rec.createNewFile(); }
				java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(rec, true));
				bw.write(r.output());
				bw.newLine();
				bw.flush();
				bw.close();
				
			} catch(Exception e) {
				ok = false;
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
				continue;
			}
		}
		
		this.clear();
		return ok;
	}
	public boolean save(int amount) {
		com.FileManagerX.Interfaces.IIterator<com.FileManagerX.BasicModels.Record> it = this.getIterator();
		boolean ok = true;
		while(this.size() > amount) {
			com.FileManagerX.BasicModels.Record r = it.getNext();
			it.remove();
			String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + "\\" +
					com.FileManagerX.Tools.Time.ticks2ShortTime_Date(r.getTime()) + ".rec";
			java.io.File rec = new java.io.File(url);
			try {
				if(!rec.exists()) { rec.createNewFile(); }
				java.io.BufferedWriter bw = new java.io.BufferedWriter(new java.io.FileWriter(rec, true));
				bw.write(r.output());
				bw.newLine();
				bw.flush();
				bw.close();
			} catch(Exception e) {
				ok = false;
				com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_WRITE_FAILED.register(e.toString());
				continue;
			}
		}
		return ok;
	}
	public boolean deleteAgoRecords(int permitAmount) {
		boolean ok = true;
		java.io.File logFolder = new java.io.File(com.FileManagerX.Tools.Pathes.getFolder_REC());
		java.io.File[] files = logFolder.listFiles();
		java.util.List<Long> time = new java.util.ArrayList<Long>();
		for(java.io.File f : files) {
			if(com.FileManagerX.Tools.Url.getExtension(f.getName()).equals(".rec")) {
				long t = com.FileManagerX.Tools.Time.shortTimeDate2Ticks(
						com.FileManagerX.Tools.Url.getNameWithoutExtension(f.getName())
						);
				if(t > 0) {
					time.add(t);
				}
			}
		}
		if(time.size() > permitAmount) {
			java.util.Collections.sort(time);
			for(int i=permitAmount; i<time.size(); i++) {
				String url = com.FileManagerX.Tools.Pathes.getFolder_REC() + 
						"\\" + 
						com.FileManagerX.Tools.Time.ticks2ShortTime_Date(time.get(i)) +
						".rec";
				java.io.File f = new java.io.File(url);
				try {
					ok &= f.delete();
				} catch(Exception e) {
					com.FileManagerX.BasicEnums.ErrorType.COMMON_FILE_OPERATE_FAILED.register(
							"Delete Rec File Failed",
							e.toString()
							);
					ok = false;
				}
			}
		}
		return ok;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static class KeyForType implements com.FileManagerX.Interfaces.ICollection.IKey {
		public Object getKey(Object item) {
			if(item instanceof com.FileManagerX.BasicModels.Record) {
				com.FileManagerX.BasicModels.Record i = (com.FileManagerX.BasicModels.Record)item;
				return i.getType();
			}
			return null;
		}
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
