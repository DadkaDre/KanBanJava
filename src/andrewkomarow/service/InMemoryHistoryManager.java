package andrewkomarow.service;

import andrewkomarow.exception.NotFoundException;
import andrewkomarow.model.Node;
import andrewkomarow.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> historyList;
    private final Map<Integer, Node> historyMap;
    Node head;
    Node tail;

    public InMemoryHistoryManager() {
        historyMap = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (historyMap.get(task.getId()) == null) {
            linkLast(task);
        } else {
            remove(task.getId());
            linkLast(task);
        }

    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {

        Node node = historyMap.get(id);
        if (node == null) {
            System.out.println(("Нет такой задачи в истории  по id: " + id));
        } else {
            removeNode(node);
        }
    }

    private void removeNode(Node node) {
        Node prev = node.prev;
        Node next = node.next;

        if (node.prev == null) {
            head = node.next;
        } else {
            prev.next = next;
        }
        if (node.next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        historyMap.remove(node.item.getId());
    }

    private void linkLast(Task task) {
        final Node l = tail;
        final Node newNode = new Node(l, task, null);
        tail = newNode;
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        historyMap.put(task.getId(), newNode);
    }

    private List<Task> getTasks() {
        historyList = new ArrayList<>();
        Node current = head;
        while (current != null) {
            historyList.add(current.item);
            current = current.next;
        }
        return historyList;
    }
}

