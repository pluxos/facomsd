using System;
using System.Collections.Generic;
using System.Net;
using System.Net.Sockets;
using ParserLibrary;

namespace Server
{
    public class ServerSocket
    {
        string data;
        
        Socket listener;
        Socket handler;
        public Queue<string> F1 { get; set; } = new Queue<string>();

        public ServerSocket()
        {
            byte[] buffer = new byte[32768];
            IPHostEntry ipHostInfo;
            IPAddress ipAddress;
            IPEndPoint localEndPoint;

            ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            ipAddress = ipHostInfo.AddressList[0];
            localEndPoint = new IPEndPoint(ipAddress, Config.Port());

            listener = new Socket(ipAddress.AddressFamily,
                SocketType.Stream, ProtocolType.Tcp);

            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(10);

                Console.WriteLine("Waiting for a connection...");
                handler = listener.Accept();
                data = null;

                int bytesRec = handler.Receive(buffer);
                if (bytesRec == 1)
                {
                    Console.WriteLine("Connection successful - I hope you know what you're doing.");
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public void Receive()
        {
            byte[] buffer = new byte[32768];

            while (true)
            {
                try
                {
                    int bytesRec = handler.Receive(buffer);
                    data = ParserClass.Deserialize<string>(buffer);
                    if (!string.IsNullOrEmpty(data))
                    {
                        F1.Enqueue(data);
                    }
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }
        }

        public void Send(string serverMessage)
        {
            byte[] buffer = new byte[32768];
            try
            {
                buffer = ParserClass.Serialize(serverMessage);
                int bytesSent = handler.Send(buffer);
            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }

        public void Disconnect()
        {
            try
            {
                handler.Shutdown(SocketShutdown.Both);
                handler.Close();

            }
            catch (Exception e)
            {
                Console.WriteLine("Unexpected exception : {0}", e.ToString());
            }
        }
    }

}

