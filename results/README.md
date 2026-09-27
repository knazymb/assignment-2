Assignment 2 — Algorithmic Analysis, Correctness and Performance Trade-offs

1. Overview

This project implements three data structures in Java:

Dynamic Array
Linked List
Min-Heap

The purpose of the assignment is to study algorithmic complexity, correctness,
data structure performance, and the difference between theoretical and
experimental results.

All three required data structures were implemented manually without using
Java's standard implementations for the main operations.



2. Complexity Analysis

Dynamic Array

| Operation | Best | Average | Worst |
|---|---|---|---|
| add(x) | Ω(1) | Θ(1) | O(n) |
| add(index, x) | Ω(1) | Θ(n) | O(n) |
| remove(index) | Ω(1) | Θ(n) | O(n) |
| get(index) | Ω(1) | Θ(1) | O(1) |
| contains(x) | Ω(1) | Θ(n) | O(n) |

The dynamic array provides constant-time random access because an element can
be accessed directly using its index.

Insertion or removal can require shifting many elements, so these operations
can take linear time.

The add(x) operation is usually constant time. However, when the internal
array becomes full, a larger array is created and the elements are copied.
Therefore, the worst case is O(n), while the amortized complexity is Θ(1).



Linked List

| Operation | Best | Average | Worst |
|---|---|---|---|
| add(x) | Ω(1) | Θ(n) | O(n) |
| add(index, x) | Ω(1) | Θ(n) | O(n) |
| remove(index) | Ω(1) | Θ(n) | O(n) |
| get(index) | Ω(1) | Θ(n) | O(n) |
| contains(x) | Ω(1) | Θ(n) | O(n) |

A linked list does not support direct access by index.

To find an element at a specific index, the list must start from the head
and follow nodes one by one.

Insertion and removal at index 0 are constant time because only the head
pointer must be changed. For other positions, the list must first traverse
the nodes.



Min-Heap

| Operation | Best | Average | Worst |
|---|---|---|---|
| insert(x) | Ω(1) | Θ(log n) | O(log n) |
| peekMin() | Ω(1) | Θ(1) | O(1) |
| extractMin() | Ω(1) | Θ(log n) | O(log n) |

A Min-Heap keeps the smallest element at the root.

During insertion, an element may move from the bottom of the heap to the
root. The height of a heap is O(log n), so insertion is O(log n) in the
worst case.

During extraction, the last element is moved to the root and may move down
the heap. Therefore, extractMin() is also O(log n).



3. Correctness

Loop Invariant 1 - Dynamic Array Insertion

The operation is:

text
add(index, x)