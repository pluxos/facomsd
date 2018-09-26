using System;
using System.Threading;

namespace Server
{
    class Program
    {
        static void Main(string[] args)
        {
            Server s = new Server();

            Thread thread = new Thread(() =>
            {
                s.Restore();
            });
            thread.Start();
            thread.Join();

            new Thread(() =>
            {
                s.Listen();
            }).Start();

            new Thread(() =>
            {
                s.Replicate();
            }).Start();

            new Thread(() =>
            {
                s.Persist();
            }).Start();

            new Thread(() =>
            {
                s.Execute();
            }).Start();
        }
    }
}
