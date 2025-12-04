package org.example.importer;


public abstract class FileImporter {
    public final void importFile(String path) {
        String raw = read(path);
        Object parsed = parse(raw);
        save(parsed);
    }


    protected String read(String path) {
        return "file-data";
    }


    protected abstract Object parse(String raw);
    protected abstract void save(Object data);
}