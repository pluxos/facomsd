using System;

namespace Client
{
    class Client
    {
        ClientSocket cs;

        public Client()
        {
            cs = new ClientSocket();
        }

        public void Listen()
        {
            while (true)
            {
                cs.ReceiveMessage();
            }
        }

        bool isValidAnswer(string aws)
        {
            //TODO Validar resposta.
            if (string.Compare(aws, "exit") == 0)
            {
                return false;
            }
            cs.SendMessage(aws);
            return true;
        }

        public void ReadIO()
        {
            while (true)
            {
                Console.WriteLine("Welcome to the SKYNET, type exit and prepare yourself for the judgement day:");
                Console.WriteLine("Create <value>");
                Console.WriteLine("Read <value>");
                Console.WriteLine("Update <value>");
                Console.WriteLine("Delete <value>");
                string aws = Console.ReadLine();
                Console.Clear();
                if (!isValidAnswer(aws))
                {
                    break;
                }
            }
        }
    }
}
