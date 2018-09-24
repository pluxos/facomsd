using ParserLibrary;
using System;
using System.Net;
using System.Net.Sockets;

namespace Client
{
    public class ClientSocket
    {
        static byte[] buffer = new byte[1024];
        static IPHostEntry ipHostInfo;
        static IPAddress ipAddress;
        static IPEndPoint remoteEP;
        static Socket sender;

        public ClientSocket()
        {

            // Connect to a remote device.  
            try
            {
                // Establish the remote endpoint for the socket.  
                // This example uses port from the appsettings config file on the local computer.  
                ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
                ipAddress = ipHostInfo.AddressList[0];
                remoteEP = new IPEndPoint(ipAddress, Config.Port());

                // Create a TCP/IP  socket.  
                sender = new Socket(ipAddress.AddressFamily,
                    SocketType.Stream, ProtocolType.Tcp);

                sender.Connect(remoteEP);

                Console.WriteLine("Socket connected to {0}",
                    sender.RemoteEndPoint.ToString());

            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }

        public void SendMessage(string clientMessage)
        {
            // Connect the socket to the remote endpoint. Catch any errors.  
            try
            {
                buffer = ParserClass.Serialize(clientMessage);
                int bytesSent = sender.Send(buffer);

            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }

        }

        public void ReceiveMessage()
        {
            try
            {
                int bytesRec = sender.Receive(buffer);
                Console.WriteLine(ParserClass.Deserialize(buffer));
            }
            catch (Exception e)
            {
                //Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }

        public void Disconnect()
        {
            try
            {
                sender.Shutdown(SocketShutdown.Both);
                sender.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }

    }
}

