using System;
using System.Net;
using System.Net.Sockets;
using ParserLibrary;

namespace Server
{
    public class ServerSocket
    {
        byte[] buffer = new byte[1024];
        public string data { get; set; }
        IPHostEntry ipHostInfo;
        IPAddress ipAddress;
        IPEndPoint localEndPoint;
        Socket listener;
        Socket handler;


        public ServerSocket()
        {
            ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            ipAddress = ipHostInfo.AddressList[0];
            localEndPoint = new IPEndPoint(ipAddress, Config.Port());

            listener = new Socket(ipAddress.AddressFamily,
                SocketType.Stream, ProtocolType.Tcp);

            try
            {
                listener.Bind(localEndPoint);
                listener.Listen(100);
                while (true)
                {
                    Console.WriteLine("Waiting for a connection...");
                    handler = listener.Accept();
                    data = null;

                    while (true)
                    {
                        int bytesRec = handler.Receive(buffer);
                        data = ParserClass.Deserialize(buffer);
                    }
                }
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
            }
        }

        public void Send(string serverMessage)
        {
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

