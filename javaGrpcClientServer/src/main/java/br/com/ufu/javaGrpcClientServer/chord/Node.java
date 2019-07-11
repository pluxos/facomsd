package br.com.ufu.javaGrpcClientServer.chord;

import java.util.ArrayList;

import br.com.ufu.javaGrpcClientServer.ChordServiceGrpc;
import br.com.ufu.javaGrpcClientServer.CrudServiceGrpc;
import br.com.ufu.javaGrpcClientServer.InsertRequest;
import br.com.ufu.javaGrpcClientServer.JoinRequest;
import br.com.ufu.javaGrpcClientServer.JoinResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Node {
	/*
	 * private static final int m = 32; // Número de bits em um inteiro
	 * 
	 * private String address; private int port; private int id;
	 * 
	 * //private ArrayList<Integer> keys; private Finger[] finger;
	 * 
	 * private Node predecessor;
	 * 
	 * private ManagedChannel channel; private
	 * ChordServiceGrpc.ChordServiceBlockingStub stub;
	 * 
	 * public Node(String _address, int _port, int _id) { this.setAddress(_address);
	 * this.setPort(_port); this.setId(_id);
	 * 
	 * this.finger = new Finger[m+1]; //this.keys = new ArrayList<>();
	 * 
	 * this.predecessor = this; }
	 * 
	 * public void initialize(Node _node) { for(int i=1; i<=m; i++) { finger[i] =
	 * new Finger();
	 * 
	 * int j = (int) Math.pow(2, i-1);
	 * 
	 * finger[i].setStart((int) ((this.id + j) % Math.pow(2, m)));
	 * finger[i].setIntervalStart((int) ((this.id + j) % Math.pow(2, m)));
	 * finger[i].setIntervalEnd((int) ((finger[i].getIntervalStart() + j) %
	 * Math.pow(2, m))); finger[i].setNode(this); } if(_node != null) { join(_node);
	 * } else { predecessor = this; finger[1].setNode(this); } }
	 * 
	 * public void key_transfer() { int start = (this.predecessor.id) + 1; int end =
	 * this.id; int flag = (start <= end)? 0 : 1;
	 * 
	 * String l = start + " " + end + " " + flag;
	 * 
	 * try { Socket sock = new Socket(this.f[1].node.IP, this.f[1].node.port); s =
	 * new Scanner(sock.getInputStream()); p = new
	 * PrintWriter(sock.getOutputStream()); p.println("kt "+l); p.flush(); String
	 * str = s.nextLine(); String[] st = str.split(" "); for(int i=0; i<st.length;
	 * i++) { if(st[i].length()!=0) { this.keys.add(Integer.parseInt(st[i])); } }
	 * 
	 * Collections.sort(this.keys); sock.close(); } catch (IOException ex) {
	 * Logger.getLogger(Clients.class.getName()).log(Level.SEVERE, null, ex); } }
	 * 
	 * public void node_join(Node _node) { if(_node != null) {
	 * Init_finger_table(_node); update_others(); key_transfer(); } else { for(int
	 * i=1; i<=m; i++) { finger[i].setNode(this); } predecessor = this; } }
	 * 
	 * public void Init_finger_table(Node _node) {
	 * finger[1].setNode(Node_Query(_node.address, _node.port, "fs " +
	 * finger[1].getStart()));
	 * 
	 * this.predecessor = Node_Query(finger[1].getNode().getAddress(),
	 * finger[1].getNode().getPort(), "pred");
	 * 
	 * String encode = this.address + " " + this.id + " " + this.port;
	 * 
	 * Node_Query(finger[1].getNode().getAddress(), finger[1].getNode().getPort(),
	 * "asspre " + encode);
	 * 
	 * for(int i=0; i<m; i++) { if(this.id < finger[i].getNode().getId()) {
	 * if(finger[i+1].getStart() >= this.id && finger[i+1].getStart() <
	 * finger[i].getNode().getId()) { finger[i+1].setNode(finger[i].getNode()); } }
	 * else if(this.id == finger[i].getNode().getId()) {
	 * finger[i+1].setNode(finger[i].getNode()); } else if(this.id >
	 * finger[i].getNode().getId()) { if(finger[i+1].getStart() >= this.id ||
	 * finger[i+1].getStart() < finger[i].getNode().getId()) {
	 * finger[i+1].setNode(finger[i].getNode()); } } else {
	 * finger[i+1].getNode().setId((Node_Query(_node.getAddress(), _node.getPort(),
	 * "fs " + finger[i+1].getStart())).getId()); } } }
	 * 
	 * public void update_others() { for(int i=1; i<=m; i++) { int j = (int)
	 * Math.pow(2, i-1); Node predecessor = find_predecessor((int) ((this.getId() -
	 * j + Math.pow(2, m)) % Math.pow(2, m)));
	 * 
	 * if(predecessor != this) { String encode = this.address + " " + this.id + " "
	 * + this.port + " " + i; Node_Query(predecessor.getAddress(),
	 * predecessor.getPort(), "uft " + encode); } } }
	 * 
	 * public void update_finger_table(Node _node, int i) { int id =
	 * finger[i].getNode().getId(); boolean flag = false;
	 * 
	 * if(id == this.id) { finger[i].setNode(_node); flag = true; } else if(this.id
	 * < finger[i].getNode().getId()) { if(_node.getId() >= this.id && _node.getId()
	 * < finger[i].getNode().getId()) { finger[i].setNode(_node); flag = true; } }
	 * else if(this.id == finger[i].getNode().getId()) { finger[i].setNode(_node);
	 * flag = true; } else if(this.id > finger[i].getNode().getId()) {
	 * if(_node.getId() >= this.id || _node.getId() < finger[i].getNode().getId()) {
	 * finger[i].setNode(_node); flag = true; } }
	 * 
	 * if(flag == true && this.predecessor != this && this.predecessor != _node) {
	 * Node n = new Node(_node.getAddress(), _node.getPort(), _node.getId());
	 * update_finger_table(n, i); } }
	 * 
	 * public Node closest_preceding_finger(int _id) { for(int i=m-1; i>=0; i--) {
	 * if(this.id < _id) { if(finger[i].getNode().getId() > this.id &&
	 * finger[i].getNode().getId() < _id) { return finger[i].getNode(); } } else
	 * if(this.id == _id) { return finger[i].getNode(); } else if(this.id > _id) {
	 * if(finger[i].getNode().getId() > this.id || finger[i].getNode().getId() <
	 * _id) { return finger[i].getNode(); } } } return this; }
	 * 
	 * public Node find_successor(int _id) { Node predecessor =
	 * find_predecessor(_id); Node successor = Node_Query(predecessor.getAddress(),
	 * predecessor.getPort(), "succ");
	 * 
	 * return successor; }
	 * 
	 * public Node find_predecessor(int _id) { Node node = this; Node predecessor =
	 * finger[1].getNode();
	 * 
	 * while(true) { if(node.getId() == this.id) { if(node.getId() <
	 * predecessor.getId()) { if(_id <= node.getId() || _id >
	 * finger[1].getNode().getId()) { node = closest_preceding_finger(_id);
	 * 
	 * if(node.getId() == this.id) { break; } } else { break; } } else
	 * if(node.getId() == predecessor.getId()) { break; } else if(node.getId() >
	 * predecessor.getId()) { if(_id <= node.getId() && _id >
	 * finger[1].getNode().getId()) { node = closest_preceding_finger(_id);
	 * 
	 * if(node.getId() == this.id) { break; } } else { break; } } } else { Node x =
	 * Node_Query(node.getAddress(), node.getPort(), "succ");
	 * 
	 * if(node.getId() < x.getId()) { if(_id <= node.getId() || _id > x.getId()) {
	 * node = Node_Query(node.getAddress(), node.getPort(), "cpf " + _id);
	 * 
	 * if(node.getId() == this.id) { break; } } else { break; } } else
	 * if(node.getId() == x.getId()) { break; } else if(node.getId()>
	 * predecessor.getId()) { if(_id <= node.getId() && _id > x.getId()) { node =
	 * Node_Query(node.getAddress(), node.getPort(), "cpf " + _id);
	 * 
	 * if(node.getId() == this.id) { break; } } else { break; } } else { break; } }
	 * } return node; }
	 * 
	 * public void join(Node _node) { this.predecessor = null; channel =
	 * ManagedChannelBuilder .forAddress(_node.getAddress(),
	 * _node.getPort()).usePlaintext().build();
	 * 
	 * stub = ChordServiceGrpc.newBlockingStub(channel);
	 * 
	 * JoinResponse response =
	 * stub.join(JoinRequest.newBuilder().setId(this.id).build());
	 * finger[1].setNode(new Node(response.getAddress(), response.getPort(),
	 * response.getId())); }
	 * 
	 * public void Stabilize() { Node x = this.predecessor; Node p =
	 * finger[1].getNode();
	 * 
	 * if(this.id < p.getId()) { if(x.getId() > this.id && x.getId() < p.getId()) {
	 * finger[1].setNode(x); } } else if(this.id == p.getId()) {
	 * finger[1].setNode(x); } else { if(x.getId() > this.id || x.getId() <
	 * finger[1].getNode().getId()) { finger[1].setNode(x); } }
	 * 
	 * String encode = this.address + " " + this.id + " " + this.port;
	 * Node_Query(finger[1].getNode().getAddress(), finger[1].getNode().getPort(),
	 * "not " + encode); }
	 * 
	 * public void notify(Node _node) { if(this.predecessor != null) {
	 * if(this.predecessor.getId() < this.id) { if(_node.getId() >
	 * this.predecessor.getId() && _node.getId() < this.id) { this.predecessor =
	 * _node; } } else if(this.predecessor.getId() == this.id) { this.predecessor =
	 * _node; } else { if(_node.getId() > this.predecessor.getId() || _node.getId()
	 * < this.id) { this.predecessor = _node; } } } else { this.predecessor = _node;
	 * } }
	 * 
	 * public void fix_fingers() { for(int i=1; i<=3; i++) {
	 * finger[i].setNode(find_successor(finger[i].getStart())); } }
	 * 
	 * public Node Node_Query(String _address, int _port, String msg) { if(port ==
	 * this.port) { String[] str = msg.split(" "); ArrayList<String> str_1 = new
	 * ArrayList<>();
	 * 
	 * for(int i=0; i<str.length; i++) { if(str[i].length()!=0) { str_1.add(str[i]);
	 * } }
	 * 
	 * switch(str_1.get(0)) { case "cpf": return
	 * closest_preceding_finger(Integer.parseInt(str_1.get(1)));
	 * 
	 * case "succ": return finger[1].getNode();
	 * 
	 * case "asspre": this.predecessor = this; break;
	 * 
	 * case "pred": return this.predecessor;
	 * 
	 * case "uft": String y="";
	 * 
	 * for(int i=1; i<str_1.size()-1; i++) { y = y + str_1.get(i)+" "; }
	 * 
	 * Node n = make_Node(y);
	 * update_finger_table(n,Integer.parseInt(str_1.get(str_1.size()-1))); break; }
	 * } String[] str = msg.split(" ");
	 * 
	 * 
	 * try {
	 * 
	 * Socket sock = new Socket(IP, port); s = new Scanner(sock.getInputStream()); p
	 * = new PrintWriter(sock.getOutputStream()); p.println(msg); p.flush(); String
	 * str = s.nextLine(); Node q=null; if(str.length()!=0) { q = make_Node(str); }
	 * sock.close(); return q; } catch (IOException ex) {
	 * Logger.getLogger(Clients.class.getName()).log(Level.SEVERE, null, ex); }
	 * return null; }
	 * 
	 * public String getAddress() { return this.address; }
	 * 
	 * public void setAddress(String _address) { this.address = _address; }
	 * 
	 * public int getId() { return this.id; }
	 * 
	 * public void setId(int _id) { this.id = _id; }
	 * 
	 * public int getPort() { return this.port; }
	 * 
	 * public void setPort(int _port) { this.port = _port; }
	 */
}
