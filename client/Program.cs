using System;
using System.Threading;

namespace client
{
    class Program
    {
        static void Main(string[] args)
        {
            ClientSocket.StartClient("T-1000");
            // Client c = new Client();
            // new Thread(() =>
            // {
            // Console.WriteLine("Thread 1");
            //     c.listenClient();
            // }).Start();

            // new Thread(() =>
            // {
            // Console.WriteLine("Thread 2");
                
            // }).Start();
        }
    }
}
