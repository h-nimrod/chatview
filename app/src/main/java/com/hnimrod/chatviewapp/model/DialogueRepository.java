package com.hnimrod.chatviewapp.model;

import com.hnimrod.chatview.ChatEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DialogueRepository {

    private DialogueRepository() {}

    public static DialogueRepository getInstance() {
        if (instance == null) {
            instance = new DialogueRepository();
        }
        return instance;
    }

    private static DialogueRepository instance;

    private List<ChatEntity> repository = new ArrayList<>();

    public List<ChatEntity> getList() {
        return repository;
    }

    public void add(ChatEntity item) {
        repository.add(item);
    }

    public void addAll(Collection<ChatEntity> collection) {
        repository.addAll(collection);
    }

    public void clear() {
        repository.clear();
    }
}
