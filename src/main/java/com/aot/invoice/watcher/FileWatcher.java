package com.aot.invoice.watcher;
import com.aot.invoice.service.InvoiceFileService;

import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class FileWatcher {
    private final WatchService watcher;
    private final Map<WatchKey,Path> keys;
    private boolean trace = false;

    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    private void register(Path dir) throws IOException {
        //WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        WatchKey key = dir.register(watcher, ENTRY_CREATE);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
    }

    public FileWatcher(Path dir) throws IOException {
        this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey,Path>();
        register(dir);
        this.trace = true;
        checkForFiles();
    }

    void checkForFiles() {
        for (;;) {
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind kind = event.kind();

                if (kind == OVERFLOW) {
                    continue;
                }

                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name);
                System.out.format("%s: %s\n", event.kind().name(), child);
                try {
                    new InvoiceFileService().triggerInvoiceEvent(child.toString());
                    TimeUnit.MILLISECONDS.sleep(2000L);
                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }
            }

          boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }

    public static void fileWatcher(String dirArg) throws IOException {

    }

}
