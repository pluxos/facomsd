using System.Threading;

namespace Client
{
    class Program
    {
        static void Main(string[] args)
        {
            Client c = new Client();
            new Thread(() =>
            {
                c.Listen();
            }).Start();

            new Thread(() =>
            {
                c.ReadIO();
                Thread.Sleep(5000);
            }).Start();
        }
    }
}
